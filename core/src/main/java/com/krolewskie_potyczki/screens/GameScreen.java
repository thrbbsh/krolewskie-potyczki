package com.krolewskie_potyczki.screens;

import com.badlogic.gdx.Screen;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.view.GameView;

public class GameScreen implements Screen {
    private final GameView view;
    private final GameController controller;
    private Arena arena;

    public GameScreen(Main game) {
        arena = new Arena();
        controller = new GameController(arena, game);
        view = new GameView(arena, controller);
    }

    @Override public void show() { view.show(); }
    @Override public void render(float delta) {
        view.render(delta);
    }
    @Override public void resize(int w, int h) {
        view.resize(w, h);
    }
    @Override public void pause() {
        view.pause();
    }
    @Override public void resume() {
        view.resume();
    }
    @Override public void hide() {
        view.hide();
    }
    @Override public void dispose() {
        view.dispose();
    }
}
