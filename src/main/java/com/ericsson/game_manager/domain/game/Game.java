package com.ericsson.game_manager.domain.game;

import com.ericsson.game_manager.domain.AggregateRoot;
import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.domain.validation.ValidationHandler;

public class Game extends AggregateRoot<GameID> {

    private String name;

    private Publisher publisher;

    private Game(final GameID gameID, final Publisher publisher, final String name) {
        super(gameID);
        this.name = name;
        this.publisher = publisher;
    }

    public static Game newGame(final String name, final Publisher publisher) {
        return new Game( //
                GameID.unique(), //
                publisher, //
                name //
        );
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public String getName() {
        return name;
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
