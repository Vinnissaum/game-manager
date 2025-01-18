package com.ericsson.game_manager.infrastructure.game.persistence;

import com.ericsson.game_manager.domain.game.Game;
import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.domain.publisher.PublisherID;
import com.ericsson.game_manager.infrastructure.MySQLGatewayTest;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Map;

@MySQLGatewayTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void givenANullGameName_whenCallSave_thenShouldReturnDataIntegrityViolation() {
        final String name = "Super Mario Bros";
        final Publisher publisher = Publisher.with(PublisherID.from("nintendo"), "Nintendo Co., Ltd.");

        final Map<LocalDate, Integer> timePlayed = Map.ofEntries(Map.entry(LocalDate.of(2025, 1, 7), 4), Map.entry(LocalDate.of(2024, 12, 27), 2));

        Game game = Game.newGame(name, publisher, timePlayed);
        GameJpaEntity jpaEntity = GameJpaEntity.from(game);
        jpaEntity.setName(null);

        DataIntegrityViolationException actualException = Assertions.assertThrows( //
                DataIntegrityViolationException.class, () -> gameRepository.saveAndFlush(jpaEntity) //
        );

        Assertions.assertInstanceOf( //
                ConstraintViolationException.class, actualException.getCause() //
        );

    }
}
