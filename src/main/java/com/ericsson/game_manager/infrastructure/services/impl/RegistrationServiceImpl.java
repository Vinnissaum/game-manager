package com.ericsson.game_manager.infrastructure.services.impl;

import com.ericsson.game_manager.infrastructure.publisher.models.RegistrationRequest;
import com.ericsson.game_manager.infrastructure.services.RegistrationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final WebClient webClient;

    private final Environment environment;

    public RegistrationServiceImpl(@Value("${external.registration.base-url}") String baseUrl, //
                                   WebClient.Builder webClientBuilder, Environment environment) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
        this.environment = environment;
    }

    @PostConstruct
    @Override
    public void register() {
        final var body = new RegistrationRequest( //
                environment.getProperty("server.host", "localhost"), //
                environment.getProperty("server.port", "8081"));

        webClient.post()
                .uri("/notification")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> System.out.println("Successfully registered: " + response))
                .doOnError(error -> System.err.println("Failed to register: " + error.getMessage()))
                .subscribe();
    }
}
