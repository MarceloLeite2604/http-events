package com.github.marceloleite2604.httpevents.service.echo;

import com.github.marceloleite2604.httpevents.mapper.ExecutionContextToPoMapper;
import com.github.marceloleite2604.httpevents.model.EchoParameters;
import com.github.marceloleite2604.httpevents.model.ExecutionContext;
import com.github.marceloleite2604.httpevents.repository.ExecutionContextRepository;
import com.github.marceloleite2604.httpevents.service.EventService;
import com.github.marceloleite2604.httpevents.service.executor.ServiceExecutor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class EchoService extends EventService {

    public EchoService(ExecutionContextRepository executionContextRepository,
                       Set<ServiceExecutor> serviceExecutors,
                       ExecutionContextToPoMapper executionContextToPoMapper) {
        super(executionContextRepository, serviceExecutors, executionContextToPoMapper);
    }

    public ExecutionContext<EchoParameters, String> echo(String value, Long delayMillis) {
        final var echoParameters = EchoParameters.builder()
                .value(value)
                .delayMillis(delayMillis)
                .build();


        final var context = buildContext("echo", echoParameters, String.class);

        return execute(context);
    }

    public Optional<ExecutionContext<EchoParameters, String>> echoVerify(UUID id) {
        return verify(id);
    }
}