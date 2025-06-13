package com.krolewskie_potyczki.controller;

import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.screens.MenuScreen;
import com.krolewskie_potyczki.view.SettingsView;

public class SettingsController {
    private final Main game;
    private final SettingsView settingsView;

    public SettingsController(Main game, SettingsView settingsView) {
        this.game = game;
        this.settingsView = settingsView;
    }

    public void update(float delta) {
        settingsView.render(delta);
    }

    public void onMenuClicked() { game.setScreen(new MenuScreen(game)); }
}
