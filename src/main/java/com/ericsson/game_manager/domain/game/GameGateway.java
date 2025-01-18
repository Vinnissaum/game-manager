package com.ericsson.game_manager.domain.game;

import com.ericsson.game_manager.domain.publisher.PublisherID;

import java.util.List;

public interface GameGateway {

    Game create(Game game);

    Game update(Game game);

    List<Game> findAll();

    List<Game> findByPublisherId(PublisherID id);
}
