package com.ericsson.game_manager.infrastructure.game.testezada;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Map;

public record GameResponse(
        @JsonProperty("id") String id,
        @JsonProperty("publisherId") String publisherId,
        @JsonProperty("name") String name,
        @JsonProperty("timePlayed") Map<LocalDate, Integer> timePlayed
) {
    @JsonCreator
    public GameResponse {
    }
}
