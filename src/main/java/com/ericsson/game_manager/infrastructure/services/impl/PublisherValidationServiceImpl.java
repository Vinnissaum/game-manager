package com.ericsson.game_manager.infrastructure.services.impl;

import com.ericsson.game_manager.application.service.PublisherValidationService;
import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.domain.publisher.PublisherID;
import com.ericsson.game_manager.infrastructure.publisher.models.PublisherResponse;
import com.ericsson.game_manager.infrastructure.services.CacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PublisherValidationServiceImpl implements PublisherValidationService {

    private final WebClient webClient;

    private final CacheService<Publisher> cacheService;

    public PublisherValidationServiceImpl(@Value("${external.validation.base-url}") String baseUrl, //
                                          WebClient.Builder webClientBuilder, CacheService<Publisher> cacheService) {
        this.cacheService = cacheService;
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public boolean isPublisherRegistered(Publisher publisher) {
        Set<Publisher> publishers = cacheService.retrieveData();

        if (publishers.isEmpty()) {
            publishers = fetchRegisteredPublishers();
            cacheService.populateCache(publishers);
        }

        return publishers != null && publishers.stream().map(item -> //
                item.getId().getValue()).collect(Collectors.toSet()).contains(publisher.getId().getValue());
    }

    private Set<Publisher> fetchRegisteredPublishers() {
        try {
            Set<PublisherResponse> response = webClient.get()
                    .uri("/publisher")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Set<PublisherResponse>>() {
                    })
                    .block();

            return response != null ? response.stream().map(publisher -> //
                    Publisher.with(PublisherID.from(publisher.id()), publisher.name())).collect(Collectors.toSet()) : null;
        } catch (Exception e) {
            throw new IllegalStateException("Error calling publisher service: " + e.getMessage());
        }
    }
}
