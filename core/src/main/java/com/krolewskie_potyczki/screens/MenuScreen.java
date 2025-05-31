package com.krolewskie_potyczki.screens;

import com.badlogic.gdx.Screen;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.controller.MenuController;
import com.krolewskie_potyczki.view.MenuView;

public class MenuScreen implements Screen {
    private final MenuController menuController;
    private final MenuView menuView;

    public MenuScreen(Main game) {
        menuView = new MenuView();
        menuController = new MenuController(game, menuView);
        menuView.setController(menuController);
    }

    @Override
    public void show() {
        menuView.show();
    }

    @Override
    public void render(float delta) {
        menuController.update(delta);
    }

    @Override
    public void resize(int w, int h) {
        menuView.resize(w, h);
    }

    @Override
    public void pause() {
        menuView.pause();
    }

    @Override
    public void resume() {
        menuView.resume();
    }

    @Override
    public void hide() {
        menuView.hide();
    }

    @Override
    public void dispose() {
        menuView.dispose();
    }
}
