package com.github.marceloleite2604.httpevents.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.marceloleite2604.httpevents.model.converter.EventStatusConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Convert;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Convert(converter = EventStatusConverter.class)
public enum ExecutionStatus {
    PENDING("pending"),
    COMPLETE("complete");

    @Getter
    @JsonValue
    private final String value;

    public static ExecutionStatus findByValue(String value) {
        if (value == null) {
            return null;
        }

        for (ExecutionStatus executionStatus : values()) {
            if (executionStatus.getValue().equals(value)) {
                return executionStatus;
            }
        }

        throw new IllegalArgumentException(String.format("Cannot find execution status for value \"%s\"", value));
    }
}
