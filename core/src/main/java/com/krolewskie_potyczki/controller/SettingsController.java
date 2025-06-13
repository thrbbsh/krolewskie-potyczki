package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.krolewskie_potyczki.AudioManager;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.screens.MenuScreen;
import com.krolewskie_potyczki.view.SettingsView;

public class SettingsController {
    private final Main game;
    private final SettingsView settingsView;
    private final Preferences prefs;

    public SettingsController(Main game, SettingsView settingsView) {
        this.game = game;
        this.settingsView = settingsView;
        prefs = Gdx.app.getPreferences("MyGameSettings");
    }

    public void update(float delta) {
        settingsView.render(delta);
    }

    public void onMenuClicked() { game.setScreen(new MenuScreen(game)); }

    public void onVolumeChanged(float vol) {
        AudioManager.inst().setVolume(vol);
        prefs.putFloat("musicVolume", vol);
        prefs.flush();
    }
}
