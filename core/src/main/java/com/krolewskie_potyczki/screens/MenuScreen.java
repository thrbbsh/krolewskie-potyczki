package com.krolewskie_potyczki.screens;

import com.badlogic.gdx.Screen;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.controller.MenuController;
import com.krolewskie_potyczki.view.MenuView;

public class MenuScreen implements Screen {
    private final MenuView view;

    public MenuScreen(Main game) {
        MenuController controller = new MenuController(game);
        this.view = new MenuView(controller);
    }

    @Override
    public void show() {view.show();}

    @Override
    public void render(float delta) {
        view.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        view.resize(width, height);
    }

    @Override
    public void pause() {
        view.pause();
    }

    @Override
    public void resume() {
        view.resume();
    }

    @Override
    public void hide() {
        view.hide();
    }

    @Override
    public void dispose() {
        view.dispose();
    }
}
