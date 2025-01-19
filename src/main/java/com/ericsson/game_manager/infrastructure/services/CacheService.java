package com.ericsson.game_manager.infrastructure.services;

import java.util.Set;

public interface CacheService<Data> {

    void populateCache(Set<Data> data);

    void clearCache();

    Set<Data> retrieveData();

}
