package com.github.marceloleite2604.httpevents.service.executor;

import com.github.marceloleite2604.httpevents.model.ExecutionContext;
import reactor.core.publisher.Mono;

public interface ServiceExecutor {

    Class<?> getServiceClass();

    void setNext(ServiceExecutor serviceExecutor);

    Mono<ExecutionContext<?, ?>> execute(ExecutionContext<?, ?> context);
}
