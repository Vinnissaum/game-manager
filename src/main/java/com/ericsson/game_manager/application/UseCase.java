package com.ericsson.game_manager.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN in);
}