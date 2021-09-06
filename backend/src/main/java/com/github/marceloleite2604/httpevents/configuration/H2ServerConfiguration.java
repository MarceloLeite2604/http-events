package com.github.marceloleite2604.httpevents.configuration;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@Slf4j
public class H2ServerConfiguration {

    @Bean(destroyMethod = "stop")
    public Server createH2WebServer(@Value("${h2.server.port}") Integer h2ConsolePort) throws SQLException {
        final var webServer = Server.createWebServer("-webPort", h2ConsolePort.toString(),
                        "-tcpAllowOthers")
                .start();
        final var message = String.format("H2 console available at %s.", webServer.getURL());
        log.info(message);
        return webServer;
    }
}
