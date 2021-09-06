package com.github.marceloleite2604.httpevents.controller;

import com.github.marceloleite2604.httpevents.mapper.ExecutionContextToDtoMapper;
import com.github.marceloleite2604.httpevents.model.ExecutionContextDto;
import com.github.marceloleite2604.httpevents.service.echo.EchoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/echo")
@RequiredArgsConstructor
public class EchoController {

    private final EchoService echoService;

    private final ExecutionContextToDtoMapper executionContextToDtoMapper;

    @GetMapping
    public ExecutionContextDto<String> get(@RequestParam String value, @RequestParam(required = false) Long delayMillis) {
        final var context = echoService.echo(value, delayMillis);
        return executionContextToDtoMapper.mapTo(context);
    }

    @GetMapping("/{id}")
    public ExecutionContextDto<String> getEvent(@PathVariable UUID id) {
        return echoService.echoVerify(id)
                .map(executionContextToDtoMapper::mapTo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
