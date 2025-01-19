package com.ericsson.game_manager.application.usecase.game.findById;

import com.ericsson.game_manager.domain.game.Game;
import com.ericsson.game_manager.domain.game.GameID;
import com.ericsson.game_manager.domain.publisher.Publisher;

import java.time.LocalDate;
import java.util.Map;

public record FindGameByIdOutput( //
                                  GameID id,
                                  String name, //
                                  Publisher publisher, //
                                  Map<LocalDate, Integer> timePlayed) {

    public static FindGameByIdOutput from(final Game game) {
        return new FindGameByIdOutput(//
                game.getId(), //
                game.getName(), //
                game.getPublisher(), //
                game.getTimePlayed() //
        );
    }
}
