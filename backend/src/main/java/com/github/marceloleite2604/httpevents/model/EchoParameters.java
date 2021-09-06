package com.github.marceloleite2604.httpevents.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EchoParameters {

    private final String value;

    private final Long delayMillis;
}
