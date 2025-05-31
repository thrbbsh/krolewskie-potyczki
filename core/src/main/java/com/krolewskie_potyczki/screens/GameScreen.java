package com.krolewskie_potyczki.screens;

import com.badlogic.gdx.Screen;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.view.GameView;

public class GameScreen implements Screen {
    private final GameController gameController;
    private final GameView gameView;

    public GameScreen(Main game) {
        gameView = new GameView();
        gameController = new GameController(game, gameView);
        gameView.setController(gameController);
    }

    @Override
    public void show() {
        gameView.show();
    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
    }

    @Override
    public void resize(int w, int h) {
        gameView.resize(w, h);
    }

    @Override
    public void pause() {
        gameView.pause();
    }

    @Override
    public void resume() {
        gameView.resume();
    }

    @Override
    public void hide() {
        gameView.hide();
    }

    @Override
    public void dispose() {
        gameView.dispose();
    }
}
