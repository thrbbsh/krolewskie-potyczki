package com.krolewskie_potyczki.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.krolewskie_potyczki.Main;

public class GameView implements Disposable {
    private Texture background;
    private SpriteBatch batch;
    private Stage stage;

    public GameView(Main game) {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("images/pexels-pixabay-104827.jpg"));
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.3f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background,
            0, 0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void pause() { }
    public void resume() { }
    public void hide() { }
}
