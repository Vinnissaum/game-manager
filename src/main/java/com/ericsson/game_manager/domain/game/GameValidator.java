package com.ericsson.game_manager.domain.game;

import com.ericsson.game_manager.domain.validation.Error;
import com.ericsson.game_manager.domain.validation.ValidationHandler;
import com.ericsson.game_manager.domain.validation.Validator;

import java.time.LocalDate;
import java.util.Map;

public class GameValidator extends Validator {

    private static final int NAME_MAX_LENGTH = 20;
    private static final int NAME_MIN_LENGTH = 3;
    private final Game game;

    public GameValidator(final Game game, final ValidationHandler handler) {
        super(handler);
        this.game = game;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        validatePublisher();
        validateTimePlayed();
    }

    private void validatePublisher() {
        final Error error = new Error("'publisher' should be specified");

        if (this.game.getPublisher() == null) {
            this.validationHandler().append(error);
            return;
        }

        final String publisherId = this.game.getPublisher().getId().getValue();
        if (publisherId == null || publisherId.trim().isBlank()) {
            this.validationHandler().append(error);
        }
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

    private void validateTimePlayed() {
        Map<LocalDate, Integer> timePlayed = this.game.getTimePlayed();

        if (timePlayed == null) {
            this.validationHandler().append(new Error("'TimePlayed' should not be null"));
            return;
        }

        timePlayed.forEach((key, value) -> {
            if (value == null || value == 0) {
                this.validationHandler().append(new Error("'Hours of TimePlayed' should be specified"));
            }
        });
    }
}
