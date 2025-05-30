package com.krolewskie_potyczki.screens;

import com.badlogic.gdx.Screen;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.MatchResult;
import com.krolewskie_potyczki.view.EndGameView;
import com.krolewskie_potyczki.view.GameView;

public class EndGameScreen implements Screen {
    private final EndGameView view;

    public EndGameScreen(GameController controller, MatchResult result) {
        view = new EndGameView(controller, result);
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
