package com.krolewskie_potyczki.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.view.GameView;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {
    private Main game;
    public GameView view;

    public GameScreen(Main game) {
        this.game = game;
        view = new GameView(game);
    }

    @Override public void show() {

    }

    @Override
    public void render(float delta) {
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
