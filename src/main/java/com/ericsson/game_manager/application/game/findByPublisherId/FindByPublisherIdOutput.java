package com.ericsson.game_manager.application.game.findByPublisherId;

import com.ericsson.game_manager.domain.game.Game;
import com.ericsson.game_manager.domain.publisher.Publisher;

import java.time.LocalDate;
import java.util.Map;

public record FindByPublisherIdOutput( //
                               String name, //
                               Publisher publisher, //
                               Map<LocalDate, Integer> timePlayed) {

    public static FindByPublisherIdOutput from(final Game game) {
        return new FindByPublisherIdOutput( //
                game.getName(), //
                game.getPublisher(), //
                game.getTimePlayed() //
        );
    }
}