package com.krolewskie_potyczki.screens;

import com.badlogic.gdx.Screen;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.MatchResult;
import com.krolewskie_potyczki.view.EndGameView;

public class EndGameScreen implements Screen {
    private final EndGameView endGameView;

    public EndGameScreen(GameController gameController, MatchResult result) {
        endGameView = new EndGameView(result);
        endGameView.setController(gameController);
    }

    @Override public void show() { endGameView.show(); }
    @Override public void render(float delta) {
        endGameView.render(delta);
    }
    @Override public void resize(int w, int h) {
        endGameView.resize(w, h);
    }
    @Override public void pause() {
        endGameView.pause();
    }
    @Override public void resume() {
        endGameView.resume();
    }
    @Override public void hide() {
        endGameView.hide();
    }
    @Override public void dispose() {
        endGameView.dispose();
    }
}
