package com.github.marceloleite2604.httpevents.model.converter;

import com.github.marceloleite2604.httpevents.model.ExecutionStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EventStatusConverter implements AttributeConverter<ExecutionStatus, String> {

    @Override
    public String convertToDatabaseColumn(ExecutionStatus executionStatus) {
        return executionStatus.getValue();
    }

    @Override
    public ExecutionStatus convertToEntityAttribute(String value) {
        return ExecutionStatus.findByValue(value);
    }
}