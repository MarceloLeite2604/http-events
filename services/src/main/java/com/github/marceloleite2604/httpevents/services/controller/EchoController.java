package com.github.marceloleite2604.httpevents.services.controller;

import com.github.marceloleite2604.httpevents.services.service.EchoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EchoController.BASE_PATH)
@RequiredArgsConstructor
public class EchoController {

    static final String BASE_PATH = "/echo";

    private final EchoService echoService;

    @GetMapping
    public String get(@RequestParam String value, @RequestParam(required = false) Long delayMillis) {
        return echoService.echo(value, delayMillis);
    }
}
