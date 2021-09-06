package com.github.marceloleite2604.httpevents.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Builder
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Getter
public class ExecutionContextPo {

    @Id
    @Column
    private final UUID id;

    @Column
    private final String service;

    @Column
    private final String executor;

    @Column
    private final String parameters;

    @Column
    private final String parametersClass;

    @Column
    private ExecutionStatus status;

    @Column
    private long timestamp;

    @Column
    private String result;

    @Column
    private String resultClass;
}
