package com.ericsson.game_manager.infrastructure.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface PublisherNotificationAPI {

    @DeleteMapping("/publishercache")
    @ResponseStatus(HttpStatus.OK)
    void clearCache();

}
