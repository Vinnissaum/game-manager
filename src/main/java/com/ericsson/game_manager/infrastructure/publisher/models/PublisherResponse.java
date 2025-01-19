package com.ericsson.game_manager.infrastructure.publisher.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublisherResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name
) {
}
