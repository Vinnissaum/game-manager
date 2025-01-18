package com.ericsson.game_manager.application.game.findByPublisherId;

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
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindByPublisherIdUseCaseTest {

    @InjectMocks
    private FindByPublisherIdUseCaseImpl useCase;

    @Mock
    private GameGateway gameGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(gameGateway);
    }

    @Test
    public void givenAValidPublisherId_whenCallsFindByPublisher_thenShouldReturnGames() {
        final PublisherID publisherID = PublisherID.from("nintendo");
        final Publisher publisher = Publisher.with(publisherID, "Nintendo Co., Ltd.");
        final Map<LocalDate, Integer> timePlayed = Map.ofEntries( //
                Map.entry(LocalDate.of(2025, 1, 7), 4), //
                Map.entry(LocalDate.of(2024, 12, 27), 2) //
        );

        List<Game> gameList = List.of( //
                Game.newGame("Super Mario Bros", publisher, timePlayed), //
                Game.newGame("The Legend of Zelda", publisher, timePlayed)
        );

        when(gameGateway.findByPublisherId(eq(publisherID))).thenReturn(gameList);
        List<FindByPublisherIdOutput> outputs = useCase.execute(publisherID.getValue());
        Mockito.verify(gameGateway, times(1)).findByPublisherId(eq(publisherID));
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var expectedId = PublisherID.from("123");
        final var expectedErrorMessage = "Gateway Error";

        when(gameGateway.findByPublisherId(eq(expectedId))).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(IllegalStateException.class, //
                () -> useCase.execute(expectedId.getValue()) //
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
