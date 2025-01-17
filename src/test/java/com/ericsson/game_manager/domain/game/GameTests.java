package com.ericsson.game_manager.domain.game;

import com.ericsson.game_manager.domain.exceptions.DomainException;
import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.domain.publisher.PublisherID;
import com.ericsson.game_manager.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTests {

    @Test
    public void givenAllValidParams_whenCallNewGame_shouldInstantiateAGame() {
        final String name = "Super Mario Bros";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final Game game = Game.newGame(name, publisher);

        Assertions.assertNotNull(game);
        Assertions.assertNotNull(publisher);
        Assertions.assertEquals(name, game.getName());
        Assertions.assertEquals(publisher, game.getPublisher());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewGameAndValidate_shouldReturnError() {
        final String name = null;
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' should not be null";

        final Game game = Game.newGame(name, publisher);

        final DomainException ex = Assertions.assertThrows( //
                DomainException.class, () -> game.validate(new ThrowsValidationHandler()) //
        );
        Assertions.assertNotNull(ex.getErrors());
        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().getFirst().message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewGameAndValidate_shouldReturnError() {
        final String name = "     ";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' should not be empty";

        final Game game = Game.newGame(name, publisher);

        final DomainException ex = Assertions.assertThrows( //
                DomainException.class, () -> game.validate(new ThrowsValidationHandler()) //
        );
        Assertions.assertNotNull(ex.getErrors());
        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().getFirst().message());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewGameAndValidate_shouldReturnError() {
        final String name = " Su ";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' must be between 3 and 20 characters";

        final Game game = Game.newGame(name, publisher);

        final DomainException ex = Assertions.assertThrows( //
                DomainException.class, () -> game.validate(new ThrowsValidationHandler()) //
        );
        Assertions.assertNotNull(ex.getErrors());
        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().getFirst().message());
    }

    @Test
    public void givenAnInvalidNameLengthGreaterThan20_whenCallNewGameAndValidate_shouldReturnError() {
        final String name = " Suuuuuuuuuuuuuuuuuuuuuuuuuper ";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' must be between 3 and 20 characters";

        final Game game = Game.newGame(name, publisher);

        final DomainException ex = Assertions.assertThrows( //
                DomainException.class, () -> game.validate(new ThrowsValidationHandler()) //
        );
        Assertions.assertNotNull(ex.getErrors());
        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().getFirst().message());
    }

    @Test
    public void givenANullPublisher_whenCallNewGameAndValidate_shouldReturnError() {
        final String name = "Super Mario Bros";
        final Publisher publisher = null;

        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'publisher' should be specified";

        final Game game = Game.newGame(name, publisher);

        final DomainException ex = Assertions.assertThrows( //
                DomainException.class, () -> game.validate(new ThrowsValidationHandler()) //
        );
        Assertions.assertNotNull(ex.getErrors());
        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().getFirst().message());
    }
}
