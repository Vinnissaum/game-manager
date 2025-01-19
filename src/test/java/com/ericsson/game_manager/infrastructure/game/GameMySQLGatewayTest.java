package com.ericsson.game_manager.infrastructure.game;

import com.ericsson.game_manager.domain.game.Game;
import com.ericsson.game_manager.domain.game.GameID;
import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.domain.publisher.PublisherID;
import com.ericsson.game_manager.infrastructure.MySQLGatewayTest;
import com.ericsson.game_manager.infrastructure.game.persistence.GameJpaEntity;
import com.ericsson.game_manager.infrastructure.game.persistence.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@MySQLGatewayTest
public class GameMySQLGatewayTest {

    @Autowired
    private GameMySQLGateway gameGateway;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void givenAValidGame_whenCallsCreate_shouldReturnANewGame() {
        final String name = "Super Mario Bros";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(Map.entry(LocalDate.of(2025, 1, 7), 4), Map.entry(LocalDate.of(2024, 12, 27), 2));

        Assertions.assertEquals(0, gameRepository.count());

        Game game = Game.newGame(name, publisher, timePlayed);

        Game actualGame = gameGateway.create(game);

        Assertions.assertEquals(1, gameRepository.count());
        Assertions.assertNotNull(actualGame);
        Assertions.assertEquals(name, actualGame.getName());
        Assertions.assertEquals(publisher.getId().getValue(), actualGame.getPublisher().getId().getValue());

        GameJpaEntity entity = gameRepository.findById(actualGame.getId().getValue()).get();
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(name, entity.getName());
        Assertions.assertEquals(publisher.getId().getValue(), entity.getPublisherId());
    }

    @Test
    public void givenAValidGameIdNotStored_whenCallsFindById_shouldReturnEmpty() {
        Assertions.assertEquals(0, gameRepository.count());

        final var actualCategory = gameGateway.findById(GameID.from("empty"));

        Assertions.assertTrue(actualCategory.isEmpty());
    }

    @Test
    public void givenAValidGamePersisted_whenCallsFindById_shouldReturnIt() {
        final String name = "Super Mario Bros";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(Map.entry(LocalDate.of(2025, 1, 7), 4), Map.entry(LocalDate.of(2024, 12, 27), 2));

        Assertions.assertEquals(0, gameRepository.count());

        Game game = Game.newGame(name, publisher, timePlayed);

        gameGateway.create(game);

        Assertions.assertEquals(1, gameRepository.count());

        Game actualGame = gameGateway.findById(game.getId()).get();

        Assertions.assertNotNull(actualGame);
        Assertions.assertEquals(game, actualGame);
        Assertions.assertEquals(name, actualGame.getName());
        Assertions.assertEquals(publisher.getId(), actualGame.getPublisher().getId());
    }

    @Test
    public void givenATwoGamesPersisted_whenCallsFindByAll_shouldReturnTheGameList() {
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");
        final Map<LocalDate, Integer> timePlayed = Map.ofEntries( //
                Map.entry(LocalDate.of(2025, 1, 7), 4), //
                Map.entry(LocalDate.of(2024, 12, 27), 2) //
        );


        Game game1 = Game.newGame("Super Mario Bros", publisher, timePlayed);
        Game game2 = Game.newGame("The Legend of Zelda", publisher, timePlayed);

        Assertions.assertEquals(0, gameRepository.count());

        gameGateway.create(game1);
        gameGateway.create(game2);

        Assertions.assertEquals(2, gameRepository.count());

        List<Game> list = gameGateway.findAll();

        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.size());
    }

}
