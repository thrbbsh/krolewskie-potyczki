package com.krolewskie_potyczki.controller;

import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Entity;
import com.krolewskie_potyczki.model.EntityType;

public class GameController {
    private final Main game;
    private final Arena arena;
    public GameController(Main game, Arena arena) {
        this.game = game;
        this.arena = arena;
    }
    public Entity createEntity(EntityType type, boolean isPlayersEntity, float x, float y) {
        Entity e = arena.createEntity(type, isPlayersEntity, x, y);
        arena.addEntity(e);
        return e;
    }
    public void onPauseClicked() {
        //TO_DO
    }
}
