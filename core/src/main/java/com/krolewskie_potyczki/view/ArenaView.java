package com.krolewskie_potyczki.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaView implements Disposable {
    private final Map<Entity, EntityView> entityViews;

    public ArenaView(Arena arena, Stage stage) {
        entityViews = new HashMap<>();
        for (Entity entity: arena.getActiveEntities()) {
            addEntity(entity, stage);
        }
    }

    public void render(float delta) {
        for (EntityView entityView: entityViews.values())
            entityView.render(delta);
    }

    public void show() {
        for (EntityView entityView: entityViews.values())
            entityView.show();
    }

    public void resize(int width, int height) {
        for (EntityView entityView: entityViews.values())
            entityView.resize(width, height);
    }

    @Override
    public void dispose() {
        for (EntityView entityView: entityViews.values())
            entityView.dispose();
    }

    public void pause() { }
    public void resume() { }
    public void hide() { }

    public void addEntity(Entity entity, Stage stage) {
        EntityView view = new EntityView(entity, stage);
        entityViews.put(entity, view);
    }

    public void removeEntity(Entity entity) {
        EntityView view = entityViews.remove(entity);
        if (view != null) {
            view.dispose();
        }
    }
}
