package com.ericsson.game_manager.application.game.create;

import com.ericsson.game_manager.domain.exceptions.DomainException;
import com.ericsson.game_manager.domain.game.GameGateway;
import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.domain.publisher.PublisherID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateGameUseCaseTest {

    @InjectMocks
    private CreateGameUseCaseImpl useCase;

    @Mock
    private GameGateway gameGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(gameGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateGame_shouldReturnGameId() {
        final String name = "Super Mario Bros";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(Map.entry(LocalDate.of(2025, 1, 7), 4), Map.entry(LocalDate.of(2024, 12, 27), 2));

        final var aCommand = CreateGameCommand.with(name, publisher, timePlayed);

        when(gameGateway.create(any())) //
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(gameGateway, times(1)) //
                .create(argThat(game -> Objects.equals(name, game.getName()) //
                        && Objects.equals(publisher, game.getPublisher()) //
                        && Objects.nonNull(game.getTimePlayed())) //
                );
    }

    @Test
    public void givenAnInvalidCommandWithInvalidName_whenCallsCreateGame_shouldThrowsDomainException() {
        final String name = null;
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(Map.entry(LocalDate.of(2025, 1, 7), 4), Map.entry(LocalDate.of(2024, 12, 27), 2));

        final var aCommand = CreateGameCommand.with(name, publisher, timePlayed);

        final var actualOutput = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorCount, actualOutput.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualOutput.getErrors().getFirst().message());

        Mockito.verify(gameGateway, times(0)).create(any());
    }

    @Test
    public void givenAnInvalidCommand_whenGatewayThrowsRandomException_shouldThrowsDomainException() {
        final String name = "Super Mario Bros";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");
        final var expectedErrorMessage = "Random error";

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(Map.entry(LocalDate.of(2025, 1, 7), 4), Map.entry(LocalDate.of(2024, 12, 27), 2));

        final var aCommand = CreateGameCommand.with(name, publisher, timePlayed);

        when(gameGateway.create(any())) //
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        IllegalStateException exception = Assertions.assertThrows( //
                IllegalStateException.class, () -> useCase.execute(aCommand) //
        );

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(gameGateway, times(1)).create(any());
    }
}
