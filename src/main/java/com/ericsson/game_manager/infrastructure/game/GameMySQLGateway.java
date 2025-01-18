package com.ericsson.game_manager.infrastructure.game;

import com.ericsson.game_manager.domain.game.Game;
import com.ericsson.game_manager.domain.game.GameGateway;
import com.ericsson.game_manager.domain.game.GameID;
import com.ericsson.game_manager.domain.publisher.PublisherID;
import com.ericsson.game_manager.infrastructure.game.persistence.GameJpaEntity;
import com.ericsson.game_manager.infrastructure.game.persistence.GameRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GameMySQLGateway implements GameGateway {

    private final GameRepository gameRepository;

    public GameMySQLGateway(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game create(Game game) {
        return this.gameRepository.save(GameJpaEntity.from(game)).toAggregate();
    }

    @Override
    public Game update(Game game) {
        return null;
    }

    @Override
    public List<Game> findAll() {
        return this.gameRepository.findAll().stream().map(GameJpaEntity::toAggregate).toList();
    }

    @Override
    public List<Game> findByPublisherId(PublisherID id) {
        return this.gameRepository.findByPublisherIdIgnoreCase(id.getValue()) //
                .stream().map(GameJpaEntity::toAggregate).toList();
    }

    @Override
    public Optional<Game> findById(GameID id) {
        return this.gameRepository.findById(id.getValue()).map(GameJpaEntity::toAggregate);
    }
}
