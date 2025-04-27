package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Entity;

import java.util.ArrayList;
import java.util.List;

public class ArenaView implements Disposable {
    private Texture background;
    private SpriteBatch batch;
    private Stage stage;
    private Arena arena;
    private List<EntityView> entityViews;

    public ArenaView(Main game) {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        arena = new Arena();
        entityViews = new ArrayList<>();
        for (Entity e: arena.getActiveEntities()) {
            entityViews.add(new EntityView(e));
        }
        //background = new Texture(Gdx.files.internal("images/pexels-pixabay-104827.jpg"));
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*
        batch.begin();
        batch.draw(background,
            0, 0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());
        batch.end();
        */

        stage.act(delta);
        stage.draw();
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
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
