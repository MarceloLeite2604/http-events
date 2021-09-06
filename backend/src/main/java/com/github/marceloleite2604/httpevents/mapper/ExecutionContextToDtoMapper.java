package com.github.marceloleite2604.httpevents.mapper;

import com.github.marceloleite2604.httpevents.model.ExecutionContextDto;
import com.github.marceloleite2604.httpevents.model.ExecutionContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecutionContextToDtoMapper {

    public <R> ExecutionContextDto<R> mapTo(ExecutionContext<?, R> executionContext) {

        return ExecutionContextDto.<R>builder()
                .id(executionContext.getId())
                .status(executionContext.getStatus())
                .payload(executionContext.getResult())
                .build();
    }
}
