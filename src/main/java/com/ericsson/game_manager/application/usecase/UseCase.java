package com.ericsson.game_manager.application.usecase;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN in);
}