package com.ericsson.game_manager.infrastructure.services.impl;

import com.ericsson.game_manager.application.service.PublisherValidationService;
import com.ericsson.game_manager.domain.exceptions.DomainException;
import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.infrastructure.publisher.models.PublisherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PublisherValidationServiceImpl implements PublisherValidationService {

    private final WebClient webClient;

    public PublisherValidationServiceImpl(@Value("${external.validation.base-url}") String baseUrl, //
                                          WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public boolean isPublisherRegistered(Publisher publisher) {
        Set<PublisherResponse> registeredPublishers = getRegisteredPublishers();

        return registeredPublishers != null //
                && registeredPublishers.stream().map(PublisherResponse::id).
                collect(Collectors.toSet()).contains(publisher.getId().getValue());
    }

    private Set<PublisherResponse> getRegisteredPublishers() {
        try {
            return webClient.get()
                    .uri("/publisher")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Set<PublisherResponse>>() {
                    })
                    .block();
        } catch (Exception e) {
            throw new IllegalStateException("Error calling publisher service: " + e.getMessage());
        }
    }
}
