package com.krolewskie_potyczki.screens;

import com.badlogic.gdx.Screen;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.view.GameView;

public class GameScreen implements Screen {
    private final GameController controller;

    public GameScreen(Main game) {
        controller = new GameController(game);
    }

    @Override public void show() { controller.show(); }
    @Override public void render(float delta) {
        controller.update(delta);
    }
    @Override public void resize(int w, int h) {
        controller.resize(w, h);
    }
    @Override public void pause() {
        controller.pause();
    }
    @Override public void resume() {
        controller.resume();
    }
    @Override public void hide() {
        controller.hide();
    }
    @Override public void dispose() {
        controller.dispose();
    }
}
