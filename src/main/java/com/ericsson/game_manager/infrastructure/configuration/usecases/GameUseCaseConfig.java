package com.ericsson.game_manager.infrastructure.configuration.usecases;

import com.ericsson.game_manager.application.game.create.CreateGameUseCase;
import com.ericsson.game_manager.application.game.create.CreateGameUseCaseImpl;
import com.ericsson.game_manager.application.game.findById.FindGameByIdUseCase;
import com.ericsson.game_manager.application.game.findById.FindGameByIdUseCaseImpl;
import com.ericsson.game_manager.application.game.findByPublisherId.FindByPublisherIdUseCase;
import com.ericsson.game_manager.application.game.findByPublisherId.FindByPublisherIdUseCaseImpl;
import com.ericsson.game_manager.application.game.list.ListGamesUseCase;
import com.ericsson.game_manager.application.game.list.ListGamesUseCaseImpl;
import com.ericsson.game_manager.domain.game.GameGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class GameUseCaseConfig {

    private final GameGateway gameGateway;

    public GameUseCaseConfig(final GameGateway gameGateway) {
        this.gameGateway = Objects.requireNonNull(gameGateway);
    }

    @Bean
    public CreateGameUseCase createGameUseCase() {
        return new CreateGameUseCaseImpl(gameGateway);
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
