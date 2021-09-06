package com.github.marceloleite2604.httpevents.model;

import com.github.marceloleite2604.httpevents.model.ExecutionStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ExecutionContextDto<R> {

    private final UUID id;

    private final ExecutionStatus status;

    private final R payload;
}
