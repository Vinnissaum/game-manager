package com.ericsson.game_manager.infrastructure.game.presenters;

import com.ericsson.game_manager.application.usecase.game.findById.FindGameByIdOutput;
import com.ericsson.game_manager.application.usecase.game.findByPublisherId.FindByPublisherIdOutput;
import com.ericsson.game_manager.application.usecase.game.list.ListGamesOutput;
import com.ericsson.game_manager.infrastructure.game.models.GameResponse;

public interface GameApiPresenter {

    static GameResponse present(final ListGamesOutput output) {
        return new GameResponse( //
                output.id().getValue(), output.publisher().getId().getValue(), output.name(), output.timePlayed() //
        );
    }

    static GameResponse present(final FindByPublisherIdOutput output) {
        return new GameResponse( //
                output.id().getValue(), output.publisher().getId().getValue(), output.name(), output.timePlayed() //
        );
    }

    static GameResponse present(final FindGameByIdOutput output) {
        return new GameResponse( //
                output.id().getValue(), output.publisher().getId().getValue(), output.name(), output.timePlayed() //
        );
    }
}
