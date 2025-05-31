package com.krolewskie_potyczki.controller;

import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.screens.GameScreen;
import com.krolewskie_potyczki.view.MenuView;

public class MenuController {
    private final Main game;
    private final MenuView menuView;

    public MenuController(Main game, MenuView menuView) {
        this.game = game;
        this.menuView = menuView;
    }

    public void update(float delta) {
        menuView.render(delta);
    }

    public void onPlayClicked() {
        game.setScreen(new GameScreen(game));
    }

    public void onExitClicked() {
        game.exitGame();
    }
}
