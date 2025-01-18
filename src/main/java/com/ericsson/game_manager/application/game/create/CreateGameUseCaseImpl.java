package com.ericsson.game_manager.application.game.create;

import com.ericsson.game_manager.domain.game.Game;
import com.ericsson.game_manager.domain.game.GameGateway;
import com.ericsson.game_manager.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class CreateGameUseCaseImpl extends CreateGameUseCase {

    private final GameGateway gameGateway;

    public CreateGameUseCaseImpl(final GameGateway gameGateway) {
        this.gameGateway = Objects.requireNonNull(gameGateway);
    }

    @Override
    public CreateGameOutput execute(CreateGameCommand createGameCommand) {
        Game game = Game.newGame( //
                createGameCommand.name(), //
                createGameCommand.publisher(), //
                createGameCommand.timePlayed() //
        );

        game.validate(new ThrowsValidationHandler());

        return CreateGameOutput.from(this.gameGateway.create(game));
    }
}
