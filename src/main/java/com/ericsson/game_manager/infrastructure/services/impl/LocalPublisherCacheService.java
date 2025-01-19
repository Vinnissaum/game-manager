package com.ericsson.game_manager.infrastructure.services.impl;

import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.infrastructure.services.CacheService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LocalPublisherCacheService implements CacheService<Publisher> {

    private final Set<Publisher> registeredPublishers = new HashSet<>();

    @Override
    public void populateCache(Set<Publisher> publishers) {
        registeredPublishers.addAll(publishers);

        System.out.println("Publishers successfully local cache from publisher-manager");
    }

    @Override
    public void clearCache() {
        registeredPublishers.clear();

        System.out.println("Publisher's local cache successfully cleaned");
    }

    @Override
    public Set<Publisher> retrieveData() {
        System.out.println("Retrieving publishers from local cache");
        return registeredPublishers;
    }

}
