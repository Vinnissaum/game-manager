package com.ericsson.game_manager.infrastructure.api;

import com.ericsson.game_manager.application.game.create.CreateGameOutput;
import com.ericsson.game_manager.infrastructure.game.models.CreateGameRequest;
import com.ericsson.game_manager.infrastructure.game.models.GameResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping(value = "game")
public interface GameAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = CREATED)
    CreateGameOutput create(@RequestBody CreateGameRequest request);

    @GetMapping
    @ResponseStatus(code = OK)
    List<GameResponse> list(@RequestParam(required = false) final String publisherId);

    @GetMapping("/{id}")
    @ResponseStatus(code = OK)
    GameResponse findById(@PathVariable final String id) throws Exception;

}
