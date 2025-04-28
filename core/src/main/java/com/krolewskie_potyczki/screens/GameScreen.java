package com.krolewskie_potyczki.screens;

import com.badlogic.gdx.*;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.view.GameView;

public class GameScreen implements Screen {
    private Main game;
    private Arena arena;
    public GameView view;
    private GameController controller;

    public GameScreen(Main game) {
        this.game = game;
        arena = new Arena();
        controller = new GameController(game, arena);
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
