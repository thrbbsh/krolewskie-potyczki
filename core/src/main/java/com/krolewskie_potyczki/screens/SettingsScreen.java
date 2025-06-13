package com.krolewskie_potyczki.screens;

import com.badlogic.gdx.Screen;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.controller.SettingsController;
import com.krolewskie_potyczki.view.SettingsView;

public class SettingsScreen implements Screen {
    private final SettingsController settingsController;
    private final SettingsView settingsView;

    public SettingsScreen(Main game) {
        settingsView = new SettingsView();
        settingsController = new SettingsController(game, settingsView);
        settingsView.setController(settingsController);
    }

    @Override
    public void show() {
        settingsView.show();
    }

    @Override
    public void render(float delta) {
        settingsController.update(delta);
    }

    @Override
    public void resize(int w, int h) {
        settingsView.resize(w, h);
    }

    @Override
    public void pause() {
        settingsView.pause();
    }

    @Override
    public void resume() {
        settingsView.resume();
    }

    @Override
    public void hide() {
        settingsView.hide();
    }

    @Override
    public void dispose() {
        settingsView.dispose();
    }
}
