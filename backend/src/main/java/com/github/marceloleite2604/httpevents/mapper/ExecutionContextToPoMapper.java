package com.github.marceloleite2604.httpevents.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marceloleite2604.httpevents.model.ExecutionContext;
import com.github.marceloleite2604.httpevents.model.ExecutionContextPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ExecutionContextToPoMapper implements Mapper<ExecutionContext<?, ?>, ExecutionContextPo> {

    private final ObjectMapper objectMapper;

    @Override
    public ExecutionContextPo mapTo(ExecutionContext<?, ?> executionContext) {

        if (executionContext == null) {
            return null;
        }

        final var parameters = writeAsJsonString(executionContext.getParameters());

        final var parametersClass = retrieveObjectClassCanonicalName(executionContext.getParameters());

        final var result = writeAsJsonString(executionContext.getResult());

        final var resultClass = retrieveObjectClassCanonicalName(executionContext.getResult());

        final var service = executionContext.getServiceClass()
                .getName();

        final var timestamp = Instant.now()
                .toEpochMilli();

        return ExecutionContextPo.builder()
                .id(executionContext.getId())
                .service(service)
                .executor(executionContext.getExecutorName())
                .parameters(parameters)
                .parametersClass(parametersClass)
                .result(result)
                .resultClass(resultClass)
                .status(executionContext.getStatus())
                .timestamp(timestamp)
                .build();
    }

    private String retrieveObjectClassCanonicalName(Object object) {

        if (object == null) {
            return null;
        }

        return object.getClass()
                .getCanonicalName();
    }

    @Override
    public ExecutionContext<?, ?> mapFrom(ExecutionContextPo po) {
        if (po == null) {
            return null;
        }

        final Class<?> serviceClass = retrieveClassForCanonicalName(po.getService());

        final Class<?> parametersClass = retrieveClassForCanonicalName(po.getParametersClass());

        final Object parameters = retrieveJsonStringAsObject(po.getParameters(), parametersClass);

        final Class<?> resultClass = retrieveClassForCanonicalName(po.getResultClass());

        final Object result = retrieveJsonStringAsObject(po.getResult(), resultClass);

        return ExecutionContext.builder()
                .id(po.getId())
                .executorName(po.getExecutor())
                .serviceClass(serviceClass)
                .parameters(parameters)
                .result(result)
                .status(po.getStatus())
                .build();
    }

    private Object retrieveJsonStringAsObject(String value, Class<?> clazz) {

        if (value == null || clazz == null) {
            return null;
        }

        try {
            return objectMapper.readValue(value, clazz);
        } catch (JsonProcessingException exception) {
            final var message = String.format("Exception thrown while converting \"%s\" JSON as an instance of \"%s\" class.", value, clazz);
            throw new IllegalStateException(message, exception);
        }
    }

    public String writeAsJsonString(Object object) {

        if (object == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Exception thrown while writing value as JSON string.", exception);
        }
    }

    private Class<?> retrieveClassForCanonicalName(String canonicalName) {

        if (canonicalName == null) {
            return null;
        }

        try {
            return Class.forName(canonicalName);
        } catch (ClassNotFoundException exception) {
            final var message = String.format("Exception thrown while searching class \"%s\".", canonicalName);
            throw new IllegalArgumentException(message, exception);
        }
    }
}
