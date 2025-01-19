package com.ericsson.game_manager.application.usecase.game.create;

import com.ericsson.game_manager.domain.game.Game;
import com.ericsson.game_manager.domain.game.GameID;

public record CreateGameOutput(
        GameID id
) {
    public static CreateGameOutput from(final Game game) {
        return new CreateGameOutput(game.getId());
    }
}
