package com.ericsson.game_manager.application.service;

import com.ericsson.game_manager.domain.publisher.Publisher;

public interface PublisherValidationService {
    boolean isPublisherRegistered(Publisher publisher);
}
