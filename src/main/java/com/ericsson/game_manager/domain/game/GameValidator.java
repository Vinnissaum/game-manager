package com.ericsson.game_manager.domain.game;

import com.ericsson.game_manager.domain.validation.Error;
import com.ericsson.game_manager.domain.validation.ValidationHandler;
import com.ericsson.game_manager.domain.validation.Validator;

public class GameValidator extends Validator {

    private final Game game;

    private static final int NAME_MAX_LENGTH = 20;

    private static final int NAME_MIN_LENGTH = 3;

    public GameValidator(final Game game, final ValidationHandler handler) {
        super(handler);
        this.game = game;
    }

    @Override
    public void validate() {
        if (this.game.getPublisher() == null) { //
            this.validationHandler().append(new Error("'publisher' should be specified")); //
            return;
        }
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.game.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final var length = name.trim().length();
        if (length < NAME_MIN_LENGTH || length > NAME_MAX_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 20 characters"));
        }
    }
}
