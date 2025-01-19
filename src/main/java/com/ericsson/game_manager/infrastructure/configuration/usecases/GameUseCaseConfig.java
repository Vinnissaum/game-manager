package com.ericsson.game_manager.infrastructure.configuration.usecases;

import com.ericsson.game_manager.application.service.PublisherValidationService;
import com.ericsson.game_manager.application.usecase.game.create.CreateGameUseCase;
import com.ericsson.game_manager.application.usecase.game.create.CreateGameUseCaseImpl;
import com.ericsson.game_manager.application.usecase.game.findById.FindGameByIdUseCase;
import com.ericsson.game_manager.application.usecase.game.findById.FindGameByIdUseCaseImpl;
import com.ericsson.game_manager.application.usecase.game.findByPublisherId.FindByPublisherIdUseCase;
import com.ericsson.game_manager.application.usecase.game.findByPublisherId.FindByPublisherIdUseCaseImpl;
import com.ericsson.game_manager.application.usecase.game.list.ListGamesUseCase;
import com.ericsson.game_manager.application.usecase.game.list.ListGamesUseCaseImpl;
import com.ericsson.game_manager.domain.game.GameGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class GameUseCaseConfig {

    private final GameGateway gameGateway;

    private final PublisherValidationService publisherValidationService;

    public GameUseCaseConfig(final GameGateway gameGateway, //
                             final PublisherValidationService publisherValidationService) {
        this.gameGateway = Objects.requireNonNull(gameGateway);
        this.publisherValidationService = Objects.requireNonNull(publisherValidationService);
    }

    @Bean
    public CreateGameUseCase createGameUseCase() {
        return new CreateGameUseCaseImpl(gameGateway, publisherValidationService);
    }

    @Bean
    public FindByPublisherIdUseCase findByPublisherIdUseCase() {
        return new FindByPublisherIdUseCaseImpl(gameGateway);
    }

    @Bean
    public ListGamesUseCase listGamesUseCase() {
        return new ListGamesUseCaseImpl(gameGateway);
    }

    @Bean
    public FindGameByIdUseCase findGameByIdUseCase() {
        return new FindGameByIdUseCaseImpl(gameGateway);
    }

}
