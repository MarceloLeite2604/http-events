package com.github.marceloleite2604.httpevents.service.echo;

import com.github.marceloleite2604.httpevents.model.EchoParameters;
import com.github.marceloleite2604.httpevents.model.ExecutionContext;
import com.github.marceloleite2604.httpevents.service.executor.AbstractServiceExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EchoServiceExecutor extends AbstractServiceExecutor<EchoParameters, String> {

    private final WebClient webClient;

    public EchoServiceExecutor() {
        super(EchoService.class, "echo");
        this.webClient = createWebClient();
    }

    private WebClient createWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/echo")
                .build();
    }

    @Override
    protected Mono<ExecutionContext<?, ?>> doExecute(ExecutionContext<?, ?> rawContext) {
        final var context = retrieveTypedExecutionContext(rawContext);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        final var parameters = context.getParameters();
        queryParams.add("value", parameters.getValue());
        if (parameters.getDelayMillis() != null) {
            queryParams.add("delayMillis", parameters.getDelayMillis()
                    .toString());
        }

        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.queryParams(queryParams)
                                .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    context.setResult(response);
                    return context;
                });
    }
}
