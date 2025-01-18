package com.ericsson.game_manager.domain.game;

import com.ericsson.game_manager.domain.AggregateRoot;
import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.domain.validation.ValidationHandler;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Game extends AggregateRoot<GameID> {

    private String name;

    private Publisher publisher;

    private Map<LocalDate, Integer> timePlayed;

    private Game( //
                  final GameID gameID, //
                  final Publisher publisher, //
                  final String name, //
                  final Map<LocalDate, Integer> timePlayed //
    ) {
        super(gameID);
        this.name = name;
        this.publisher = publisher;
        this.timePlayed = timePlayed;
    }

    public static Game newGame( //
                                final String name, //
                                final Publisher publisher,
                                final Map<LocalDate, Integer> timePlayed
    ) {
        return new Game( //
                GameID.unique(), //
                publisher, //
                name, //
                timePlayed
        );
    }

    public static Game with(
            final GameID gameID,
            final Publisher publisher,
            final String name,
            final Map<LocalDate, Integer> timePlayed
    ) {
        return new Game(gameID, publisher, name, timePlayed);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public String getName() {
        return name;
    }

    public Map<LocalDate, Integer> getTimePlayed() {
        return timePlayed;
    }

    public Game update(String name, Publisher publisher, Map<LocalDate, Integer> timePlayed) {
        this.name = name;
        this.publisher = publisher;
        this.timePlayed = timePlayed;

        return this;
    }

    public Game update(String name, Publisher publisher) {
        this.name = name;
        this.publisher = publisher;

        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new GameValidator(this, handler).validate();
    }
}
