package com.krolewskie_potyczki.screens;

import com.badlogic.gdx.Screen;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.view.PauseView;

public class PauseScreen implements Screen {
    private final GameController controller;
    private final PauseView pauseView;

    public PauseScreen(GameController controller)  {
        this.controller = controller;
        pauseView = new PauseView();
        pauseView.setController(controller);
    }

    public PauseView getView() {
        return pauseView;
    }

    @Override
    public void show() {
        pauseView.show();
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
    }

    @Override
    public void resize(int w, int h) {
        pauseView.resize(w, h);
    }

    @Override
    public void pause() {
        pauseView.pause();
    }

    @Override
    public void resume() {
        pauseView.resume();
    }

    @Override
    public void hide() {
        pauseView.hide();
    }

    @Override
    public void dispose() {
        pauseView.dispose();
    }
}
