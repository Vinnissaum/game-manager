package com.ericsson.game_manager.infrastructure.api.controllers;

import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.infrastructure.api.PublisherNotificationAPI;
import com.ericsson.game_manager.infrastructure.services.CacheService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherNotificationController implements PublisherNotificationAPI {

    private final CacheService<Publisher> cacheService;

    public PublisherNotificationController(final CacheService<Publisher> cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void clearCache() {
        this.cacheService.clearCache();
    }

}
