package com.ericsson.game_manager.application.game.findById;

import com.ericsson.game_manager.domain.exceptions.DomainException;
import com.ericsson.game_manager.domain.game.GameGateway;
import com.ericsson.game_manager.domain.game.GameID;
import com.ericsson.game_manager.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class FindGameByIdUseCaseImpl extends FindGameByIdUseCase {

    private final GameGateway gameGateway;

    public FindGameByIdUseCaseImpl(final GameGateway gameGateway) {
        this.gameGateway = Objects.requireNonNull(gameGateway);
    }

    @Override
    public FindGameByIdOutput execute(String id) {
        final GameID gameID = GameID.from(id);

        return this.gameGateway.findById(gameID).map(FindGameByIdOutput::from).orElseThrow(notFound(gameID));
    }

    private Supplier<DomainException> notFound(final GameID id) {
        return () -> DomainException.with( //
                new Error("Game with ID %s was not found".formatted(id.getValue())) //
        );
    }
}
