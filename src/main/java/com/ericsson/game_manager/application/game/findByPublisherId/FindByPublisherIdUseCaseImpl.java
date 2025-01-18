package com.ericsson.game_manager.application.game.findByPublisherId;

import com.ericsson.game_manager.domain.game.Game;
import com.ericsson.game_manager.domain.game.GameGateway;
import com.ericsson.game_manager.domain.publisher.PublisherID;

import java.util.List;
import java.util.Objects;

public class FindByPublisherIdUseCaseImpl extends FindByPublisherIdUseCase {

    private final GameGateway gameGateway;

    public FindByPublisherIdUseCaseImpl(final GameGateway gameGateway) {
        this.gameGateway = Objects.requireNonNull(gameGateway);
    }

    @Override
    public List<FindByPublisherIdOutput> execute(String publisherId) {
        List<Game> gameList = gameGateway.findByPublisherId(PublisherID.from(publisherId));

        return gameList.stream().map(FindByPublisherIdOutput::from).toList();
    }
}
