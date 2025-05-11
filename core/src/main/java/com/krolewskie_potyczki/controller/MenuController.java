package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.Gdx;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.screens.GameScreen;

public class MenuController {
    private final Main game;

    public MenuController(Main game) {
        this.game = game;
    }

    public void onPlayClicked() {
        game.setScreen(new GameScreen(game));
    }

    public void onExitClicked() {
        Gdx.app.exit();
    }
}
