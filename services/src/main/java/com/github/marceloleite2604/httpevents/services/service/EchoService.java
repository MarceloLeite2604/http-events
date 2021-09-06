package com.github.marceloleite2604.httpevents.services.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EchoService {

    public String echo(String value, Long delayMillis) {
        if ( delayMillis != null) {
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException exception) {
                log.error("Thread interrupted.");
                throw new IllegalStateException("Thread interrupted while delaying execution.");
            }
        }
        return value;
    }
}
