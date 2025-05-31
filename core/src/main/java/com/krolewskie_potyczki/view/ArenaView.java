package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.config.EntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaView implements Disposable {
    private final Stage stage;
    private final Map<Entity, EntityView> entityViews;
    private final SpriteBatch bgBatch;
    private final Texture bgTexture;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean drawSpawnArea = false;

    public ArenaView(Stage stage) {
        this.stage = stage;
        entityViews = new HashMap<>();
        bgBatch = new SpriteBatch();
        bgTexture = new Texture(Gdx.files.internal("images/background/game/background.png"));
    }

    public CardView createCardView(EntityType entityType, int elixirCost, int cardIdx, CardClickListener listener) {
        CardView cardView = new CardView(entityType, elixirCost, cardIdx, listener);
        cardView.addToStage(stage);
        return cardView;
    }

    public void sync(List<Entity> activeEntities) {
        activeEntities.forEach(e ->
            entityViews.computeIfAbsent(e, key -> new EntityView(key, stage))
        );
        entityViews.keySet().removeIf(key -> !activeEntities.contains(key));
    }

    public void render(float delta) {
        bgBatch.begin();
        bgBatch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        bgBatch.draw(bgTexture, 0, 0, stage.getWidth(), stage.getHeight());
        bgBatch.end();

        if (drawSpawnArea) {
            showSpawnArea();
        }

        for (EntityView entityView: entityViews.values())
            entityView.render(delta);
        stage.act(delta);
        stage.draw();
    }

    private void showSpawnArea() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.8f, 0.8f, 0.8f, 0.5f);
        shapeRenderer.rect(
            1030, 227,
            875, 835
        );
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        for (EntityView entityView: entityViews.values())
            entityView.dispose();
        stage.dispose();
    }

    public void setSpawnArea(boolean state) {
        drawSpawnArea = state;
    }
}
