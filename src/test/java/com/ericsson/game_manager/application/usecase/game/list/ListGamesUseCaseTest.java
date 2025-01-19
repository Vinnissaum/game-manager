package com.ericsson.game_manager.application.usecase.game.list;

import com.ericsson.game_manager.domain.game.Game;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListGamesUseCaseTest {

    @InjectMocks
    private ListGamesUseCaseImpl useCase;

    @Mock
    private GameGateway gameGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(gameGateway);
    }

    @Test
    public void givenAValidCall_whenCallsListGames_thenShouldReturnGames() {
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");
        final Map<LocalDate, Integer> timePlayed = Map.ofEntries( //
                Map.entry(LocalDate.of(2025, 1, 7), 4), //
                Map.entry(LocalDate.of(2024, 12, 27), 2) //
        );

        List<Game> gameList = List.of( //
                Game.newGame("Super Mario Bros", publisher, timePlayed), //
                Game.newGame("The Legend of Zelda", publisher, timePlayed)
        );

        when(gameGateway.findAll()).thenReturn(gameList);

        List<ListGamesOutput> outputList = useCase.execute();

        Assertions.assertNotNull(outputList);
        Assertions.assertFalse(outputList.isEmpty());
        Assertions.assertEquals(2, outputList.size());
    }

    @Test
    public void givenAValidCall_whenHasNoResults_thenShouldReturnGames() {
        when(gameGateway.findAll()).thenReturn(Collections.emptyList());

        List<ListGamesOutput> outputList = useCase.execute();

        Assertions.assertTrue(outputList.isEmpty());
        Assertions.assertEquals(0, outputList.size());
    }

    @Test
    public void givenAValidCall_whenCallsListGamesAndGatewayThrowsException_thenShouldReturnException() {
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");
        final Map<LocalDate, Integer> timePlayed = Map.ofEntries( //
                Map.entry(LocalDate.of(2025, 1, 7), 4), //
                Map.entry(LocalDate.of(2024, 12, 27), 2) //
        );
        final String expectedErrorMessage = "Random Error";

        List<Game> gameList = List.of( //
                Game.newGame("Super Mario Bros", publisher, timePlayed), //
                Game.newGame("The Legend of Zelda", publisher, timePlayed)
        );

        when(gameGateway.findAll()).thenThrow(new IllegalStateException(expectedErrorMessage));

        IllegalStateException output = Assertions.assertThrows(IllegalStateException.class,
                () -> useCase.execute()
        );

        Assertions.assertEquals(expectedErrorMessage, output.getMessage());
    }

}
