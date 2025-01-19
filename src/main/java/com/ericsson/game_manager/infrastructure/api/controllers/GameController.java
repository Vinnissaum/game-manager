package com.ericsson.game_manager.infrastructure.api.controllers;

import com.ericsson.game_manager.application.usecase.game.create.CreateGameCommand;
import com.ericsson.game_manager.application.usecase.game.create.CreateGameOutput;
import com.ericsson.game_manager.application.usecase.game.create.CreateGameUseCase;
import com.ericsson.game_manager.application.usecase.game.findById.FindGameByIdUseCase;
import com.ericsson.game_manager.application.usecase.game.findByPublisherId.FindByPublisherIdUseCase;
import com.ericsson.game_manager.application.usecase.game.list.ListGamesUseCase;
import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.domain.publisher.PublisherID;
import com.ericsson.game_manager.infrastructure.api.GameAPI;
import com.ericsson.game_manager.infrastructure.game.models.CreateGameRequest;
import com.ericsson.game_manager.infrastructure.game.models.GameResponse;
import com.ericsson.game_manager.infrastructure.game.presenters.GameApiPresenter;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class GameController implements GameAPI {

    private final CreateGameUseCase createGameUseCase;
    private final ListGamesUseCase listGamesUseCase;
    private final FindByPublisherIdUseCase findByPublisherIdUseCase;
    private final FindGameByIdUseCase findByIdUseCase;

    public GameController(final CreateGameUseCase createGameUseCase, //
                          final ListGamesUseCase listGamesUseCase, //
                          final FindByPublisherIdUseCase findByPublisherIdUseCase, //
                          final FindGameByIdUseCase findByIdUseCase) { //
        this.createGameUseCase = Objects.requireNonNull(createGameUseCase);
        this.listGamesUseCase = Objects.requireNonNull(listGamesUseCase);
        this.findByPublisherIdUseCase = Objects.requireNonNull(findByPublisherIdUseCase);
        this.findByIdUseCase = Objects.requireNonNull(findByIdUseCase);
    }

    @Override
    public CreateGameOutput create(CreateGameRequest request) {
        CreateGameCommand command = new CreateGameCommand( //
                request.name(), //
                Publisher.with(PublisherID.from(request.publisherId())), //
                request.timePlayed() //
        );

        return createGameUseCase.execute(command);
    }

    @Override
    public List<GameResponse> list(String publisherId) {
        if (publisherId != null && !publisherId.isEmpty()) {
            return findByPublisherIdUseCase.execute(publisherId).stream().map(GameApiPresenter::present).toList();
        }

        return listGamesUseCase.execute().stream().map(GameApiPresenter::present).toList();
    }

    @Override
    public GameResponse findById(String id) throws Exception {
        return GameApiPresenter.present(findByIdUseCase.execute(id));
    }

}
