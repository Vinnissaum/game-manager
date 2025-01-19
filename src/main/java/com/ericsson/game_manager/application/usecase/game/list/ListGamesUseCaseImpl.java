package com.ericsson.game_manager.application.usecase.game.list;

import com.ericsson.game_manager.domain.game.GameGateway;

import java.util.List;
import java.util.Objects;

public class ListGamesUseCaseImpl extends ListGamesUseCase {

    private final GameGateway gameGateway;

    public ListGamesUseCaseImpl(final GameGateway gameGateway) {
        this.gameGateway = Objects.requireNonNull(gameGateway);
    }

    @Override
    public List<ListGamesOutput> execute() {
        return this.gameGateway.findAll().stream().map(ListGamesOutput::from).toList();
    }

}
