package com.krolewskie_potyczki.controller;

import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Entity;
import com.krolewskie_potyczki.model.EntityType;
import com.krolewskie_potyczki.screens.MenuScreen;

public class GameController {
    private final Main game;
    private final Arena arena;
    private boolean paused = false;
    public GameController(Main game, Arena arena) {
        this.game = game;
        this.arena = arena;
    }
    public Entity createEntity(EntityType type, boolean isPlayersEntity, float x, float y) {
        Entity e = arena.createEntity(type, isPlayersEntity, x, y);
        arena.addEntity(e);
        return e;
    }
    public boolean isPaused() {
        return paused;
    }
    public void onPauseClicked() {
        paused = true;
    }

    public void onResumeClicked() {
        paused = false;
    }

    public void onMenuClicked() {
        game.setScreen(new MenuScreen(game));
    }
}
