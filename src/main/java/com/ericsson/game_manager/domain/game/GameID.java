package com.ericsson.game_manager.domain.game;

import com.ericsson.game_manager.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class GameID extends Identifier {

    private final String value;

    private GameID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static GameID unique() {
        return GameID.from(UUID.randomUUID());
    }

    public static GameID from(final String anId) {
        return new GameID(anId);
    }

    public static GameID from(final UUID anId) {
        return new GameID(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GameID that = (GameID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
