package com.github.marceloleite2604.httpevents.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExecutionContext<P, R> {

    private final UUID id;

    private final Class<?> serviceClass;

    private final String executorName;

    private final P parameters;

    @Setter
    private ExecutionStatus status;

    @Setter
    private R result;
}
