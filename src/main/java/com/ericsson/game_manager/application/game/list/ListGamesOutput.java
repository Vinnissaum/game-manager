package com.ericsson.game_manager.application.game.list;

import com.ericsson.game_manager.domain.game.Game;
import com.ericsson.game_manager.domain.game.GameID;
import com.ericsson.game_manager.domain.publisher.Publisher;

import java.time.LocalDate;
import java.util.Map;

public record ListGamesOutput( //
                               GameID id,
                               String name, //
                               Publisher publisher, //
                               Map<LocalDate, Integer> timePlayed) {

    public static ListGamesOutput from(final Game game) {
        return new ListGamesOutput(
                game.getId(), //
                game.getName(), //
                game.getPublisher(), //
                game.getTimePlayed() //
        );
    }
}
