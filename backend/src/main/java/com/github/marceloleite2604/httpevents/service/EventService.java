package com.github.marceloleite2604.httpevents.service;

import com.github.marceloleite2604.httpevents.mapper.ExecutionContextToPoMapper;
import com.github.marceloleite2604.httpevents.model.EchoParameters;
import com.github.marceloleite2604.httpevents.model.ExecutionContext;
import com.github.marceloleite2604.httpevents.model.ExecutionStatus;
import com.github.marceloleite2604.httpevents.repository.ExecutionContextRepository;
import com.github.marceloleite2604.httpevents.service.executor.ServiceExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public abstract class EventService {

    private final ExecutionContextRepository executionContextRepository;

    private final ServiceExecutor firstServiceExecutor;

    private final ExecutionContextToPoMapper executionContextToPoMapper;

    protected EventService(
            ExecutionContextRepository executionContextRepository,
            Set<ServiceExecutor> serviceExecutors,
            ExecutionContextToPoMapper executionContextToPoMapper) {
        this.executionContextRepository = executionContextRepository;
        this.firstServiceExecutor = createServiceExecutorsChain(serviceExecutors);
        this.executionContextToPoMapper = executionContextToPoMapper;
    }

    private ServiceExecutor createServiceExecutorsChain(Set<ServiceExecutor> genericServiceExecutors) {
        final var serviceExecutors = filterExecutorsForThisClassOnly(genericServiceExecutors);

        ServiceExecutor previous = null;
        for (ServiceExecutor current : serviceExecutors) {
            if (previous != null) {
                current.setNext(previous);
            }
            previous = current;
        }
        return previous;
    }

    private Set<ServiceExecutor> filterExecutorsForThisClassOnly(Set<ServiceExecutor> serviceExecutors) {
        return serviceExecutors.stream()
                .filter(serviceExecutor -> this.getClass()
                        .equals(serviceExecutor.getServiceClass()))
                .collect(Collectors.toSet());
    }

    protected Mono<ExecutionContext<?, ?>> create(ExecutionContext<?, ?> context) {
        context.setStatus(ExecutionStatus.PENDING);
        final var event = executionContextToPoMapper.mapTo(context);
        executionContextRepository.save(event);
        return Mono.just(context);
    }

    protected Mono<ExecutionContext<?, ?>> complete(ExecutionContext<?, ?> context) {
        context.setStatus(ExecutionStatus.COMPLETE);
        final var po = executionContextToPoMapper.mapTo(context);
        executionContextRepository.save(po);
        return Mono.just(context);
    }

    public <P, R> ExecutionContext<P, R> execute(ExecutionContext<P, R> context) {

        create(context)
                .flatMap(firstServiceExecutor::execute)
                .flatMap(this::complete)
                .subscribe();

        return context;
    }

    public <P, R> ExecutionContext<P, R> buildContext(String name, P parameters, Class<R> responseClass) {
        return ExecutionContext.<P, R>builder()
                .executorName(name)
                .parameters(parameters)
                .id(UUID.randomUUID())
                .serviceClass(this.getClass())
                .build();
    }

    @SuppressWarnings("unchecked")
    protected <P, R> Optional<ExecutionContext<P, R>> verify(final UUID id) {
        return executionContextRepository.findById(id)
                .map(executionContextToPoMapper::mapFrom)
                .map(context -> (ExecutionContext<P, R>)context);
    }
}
