package com.ericsson.game_manager.domain.game;

import com.ericsson.game_manager.domain.exceptions.DomainException;
import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.domain.publisher.PublisherID;
import com.ericsson.game_manager.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class GameTests {

    @Test
    public void givenAllValidParams_whenCallNewGame_shouldInstantiateAGame() {
        final String name = "Super Mario Bros";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(
                Map.entry(LocalDate.of(2025, 1, 7), 4),
                Map.entry(LocalDate.of(2024, 12, 27), 2)
        );
        final Game game = Game.newGame(name, publisher, timePlayed);

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

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(
                Map.entry(LocalDate.of(2025, 1, 7), 4),
                Map.entry(LocalDate.of(2024, 12, 27), 2)
        );
        final Game game = Game.newGame(name, publisher, timePlayed);

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

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(
                Map.entry(LocalDate.of(2025, 1, 7), 4),
                Map.entry(LocalDate.of(2024, 12, 27), 2)
        );
        final Game game = Game.newGame(name, publisher, timePlayed);

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

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(
                Map.entry(LocalDate.of(2025, 1, 7), 4),
                Map.entry(LocalDate.of(2024, 12, 27), 2)
        );
        final Game game = Game.newGame(name, publisher, timePlayed);

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

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(
                Map.entry(LocalDate.of(2025, 1, 7), 4),
                Map.entry(LocalDate.of(2024, 12, 27), 2)
        );
        final Game game = Game.newGame(name, publisher, timePlayed);

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

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(
                Map.entry(LocalDate.of(2025, 1, 7), 4),
                Map.entry(LocalDate.of(2024, 12, 27), 2)
        );
        final Game game = Game.newGame(name, publisher, timePlayed);

        final DomainException ex = Assertions.assertThrows( //
                DomainException.class, () -> game.validate(new ThrowsValidationHandler()) //
        );
        Assertions.assertNotNull(ex.getErrors());
        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().getFirst().message());
    }

    @Test
    public void givenAGameEntity_whenCallUpdate_shouldBeUpdated() {
        final String name = "Super Mario Bros";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");
        final String updatedName = "Sonic";
        final Publisher updatedPublisher = Publisher.with(PublisherID.from("sega"), "Sega Corporation");
        final int expectedTimePlayedCount = 3;

        Map<LocalDate, Integer> timePlayed = new HashMap<>(Map.ofEntries(
                Map.entry(LocalDate.of(2025, 1, 7), 4),
                Map.entry(LocalDate.of(2024, 12, 27), 2)
        ));
        timePlayed.put(LocalDate.now(), 2);

        Game game = Game.newGame(name, publisher, timePlayed);
        game = game.update(updatedName, updatedPublisher, timePlayed);

        Assertions.assertNotNull(game);
        Assertions.assertNotNull(publisher);
        Assertions.assertEquals(updatedName, game.getName());
        Assertions.assertEquals(updatedPublisher, game.getPublisher());
        Assertions.assertEquals(expectedTimePlayedCount, game.getTimePlayed().size());
    }

    @Test
    public void givenANullTimePlayed_whenCallNewGameAndValidate_shouldReturnError() {
        final String name = "Super Mario Bros";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'TimePlayed' should not be null";

        final Map<LocalDate, Integer> timePlayed = null;
        final Game game = Game.newGame(name, publisher, timePlayed);

        final DomainException ex = Assertions.assertThrows( //
                DomainException.class, () -> game.validate(new ThrowsValidationHandler()) //
        );
        Assertions.assertNotNull(ex.getErrors());
        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().getFirst().message());
    }

    @Test
    public void givenAnInvalidTimePlayedWithZeroHoursOfGameplay_whenCallNewGameAndValidate_shouldReturnError() {
        final String name = "Super Mario Bros";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'Hours of TimePlayed' should be specified";

        Map<LocalDate, Integer> timePlayed = new HashMap<>(Map.ofEntries(
                Map.entry(LocalDate.of(2025, 1, 7), 4),
                Map.entry(LocalDate.of(2024, 12, 27), 0)
        ));
        final Game game = Game.newGame(name, publisher, timePlayed);

        final DomainException ex = Assertions.assertThrows( //
                DomainException.class, () -> game.validate(new ThrowsValidationHandler()) //
        );
        Assertions.assertNotNull(ex.getErrors());
        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().getFirst().message());
    }

    @Test
    public void givenAnInvalidTimePlayedWithDuplicatedDate_whenCallNewGameAndValidate_shouldReturnError() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Map<LocalDate, Integer> timePlayed = Map.ofEntries(
                    Map.entry(LocalDate.of(2025, 1, 7), 4),
                    Map.entry(LocalDate.of(2025, 1, 7), 2)
            );
        });
    }
}
