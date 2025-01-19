package com.ericsson.game_manager.application.usecase.game.create;

import com.ericsson.game_manager.application.service.PublisherValidationService;
import com.ericsson.game_manager.domain.exceptions.DomainException;
import com.ericsson.game_manager.domain.game.Game;
import com.ericsson.game_manager.domain.game.GameGateway;
import com.ericsson.game_manager.domain.validation.Error;
import com.ericsson.game_manager.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class CreateGameUseCaseImpl extends CreateGameUseCase {

    private final GameGateway gameGateway;

    private final PublisherValidationService publisherValidationService;

    public CreateGameUseCaseImpl(final GameGateway gameGateway, //
                                 final PublisherValidationService publisherValidationService) {
        this.gameGateway = Objects.requireNonNull(gameGateway);
        this.publisherValidationService = Objects.requireNonNull(publisherValidationService);
    }

    @Override
    public CreateGameOutput execute(CreateGameCommand createGameCommand) {
        Game game = Game.newGame( //
                createGameCommand.name(), //
                createGameCommand.publisher(), //
                createGameCommand.timePlayed() //
        );

        if (!publisherValidationService.isPublisherRegistered(game.getPublisher())) {
            throw DomainException.with(new Error("Publisher '%s' is not registered in publisher manager" //
                    .formatted(game.getPublisher().getId().getValue())));
        }

        game.validate(new ThrowsValidationHandler());

        return CreateGameOutput.from(this.gameGateway.create(game));
    }
}
