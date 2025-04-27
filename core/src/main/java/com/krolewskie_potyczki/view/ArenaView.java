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
    private List<EntityView> entityViews;

    public ArenaView(Arena arena) {
        entityViews = new ArrayList<>();
        for (Entity entity: arena.getActiveEntities()) {
            entityViews.add(new EntityView(entity));
        }
    }

    public void render(float delta) {
        for (EntityView entityView: entityViews)
            entityView.render(delta);
    }

    public void show() {
        for (EntityView entityView: entityViews)
            entityView.show();
    }

    public void resize(int width, int height) {
        for (EntityView entityView: entityViews)
            entityView.resize(width, height);
    }

    @Override
    public void dispose() {
        for (EntityView entityView: entityViews)
            entityView.dispose();
    }

    public void pause() { }
    public void resume() { }
    public void hide() { }

    public void addEntityView(EntityView entityView) {
        entityViews.add(entityView);
    }
}
