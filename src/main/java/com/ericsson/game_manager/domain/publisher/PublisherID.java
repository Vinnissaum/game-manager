package com.ericsson.game_manager.domain.publisher;

import com.ericsson.game_manager.domain.Identifier;

import java.util.Objects;

public class PublisherID extends Identifier {

    private final String value;

    private PublisherID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static PublisherID from(String id) {
        return new PublisherID(id);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PublisherID that = (PublisherID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

}
