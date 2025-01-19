package com.ericsson.game_manager.application.usecase.game.create;

import com.ericsson.game_manager.domain.publisher.Publisher;

import java.time.LocalDate;
import java.util.Map;

public record CreateGameCommand( //
                                 String name, //
                                 Publisher publisher, //
                                 Map<LocalDate, Integer> timePlayed) {

    public static CreateGameCommand with( //
                                          final String name, //
                                          final Publisher publisher, //
                                          final Map<LocalDate, Integer> timePlayed //
    ) { //
        return new CreateGameCommand(name, publisher, timePlayed);
    }
}
