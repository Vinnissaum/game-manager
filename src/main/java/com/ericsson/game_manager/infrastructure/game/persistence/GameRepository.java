package com.ericsson.game_manager.infrastructure.game.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<GameJpaEntity, String> {

    List<GameJpaEntity> findByPublisherIdIgnoreCase(String publisherId);
}
