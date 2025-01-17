package com.ericsson.game_manager.domain.publisher;

import com.ericsson.game_manager.domain.AggregateRoot;
import com.ericsson.game_manager.domain.validation.ValidationHandler;

import java.util.Objects;

public class Publisher extends AggregateRoot<PublisherID> {

    private String name;

    private Publisher(final PublisherID id, final String name) {
        super(id);
        this.name = name;
    }

    public static Publisher with(final PublisherID id, final String name) {
        return new Publisher(id, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(getName(), publisher.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }

    @Override
    public void validate(ValidationHandler handler) {}
}
