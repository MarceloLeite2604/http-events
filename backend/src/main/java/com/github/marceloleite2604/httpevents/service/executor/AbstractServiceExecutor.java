package com.github.marceloleite2604.httpevents.service.executor;

import com.github.marceloleite2604.httpevents.model.ExecutionContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class AbstractServiceExecutor<P, R> implements ServiceExecutor {

    @Getter
    private final Class<?> serviceClass;

    private final String name;

    @Setter
    private ServiceExecutor next;

    @Override
    public Mono<ExecutionContext<?, ?>> execute(ExecutionContext<?, ?> context) {
        if (canHandle(context)) {
            return doExecute(context);
        }

        if (next != null) {
            return next.execute(context);
        }

        final var message = String.format("Cannot handle executor \"%s\" on \"%s\" service class.", context.getExecutorName(), context.getServiceClass());
        throw new IllegalStateException(message);
    }

    protected boolean canHandle(ExecutionContext<?, ?> context) {
        return serviceClass.equals(context.getServiceClass()) &&
                name.equals(context.getExecutorName());
    }

    @SuppressWarnings("unchecked")
    protected ExecutionContext<P, R> retrieveTypedExecutionContext(ExecutionContext<?, ?> context) {
        return (ExecutionContext<P, R>) context;
    }

    protected abstract Mono<ExecutionContext<?, ?>> doExecute(ExecutionContext<?, ?> context);
}
