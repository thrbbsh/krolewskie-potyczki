package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.unit.CompositeUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaView implements Disposable {
    private final Stage stage;
    private final Map<Entity, EntityView> entityViews;
    private final List<EntityView> ghostEntityViews = new ArrayList<>();
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
            entityViews.computeIfAbsent(e, key -> new EntityView(stage, e.getIsPlayersEntity(), e.getConfig().type, e.getConfig().totalHP))
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
        for (Map.Entry<Entity, EntityView> entry : entityViews.entrySet()) {
            Entity entity = entry.getKey();
            EntityView entityView = entry.getValue();
            entityView.receivePackage(entity.getPos(), entity.getHP());
            entityView.render(delta);
        }
        Vector3 cursorPos = stage.getViewport().getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        for (int i = 0; i < ghostEntityViews.size(); i++) {
            EntityView entityView = ghostEntityViews.get(i);
            if (ghostEntityViews.size() == 1) entityView.receivePackage(new Vector2(cursorPos.x, cursorPos.y), null);
                else entityView.receivePackage(CompositeUnit.calculateOffsetPosition(new Vector2(cursorPos.x, cursorPos.y), i, ghostEntityViews.size()), null);
            entityView.render(delta);
        }
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

    public void showGhostEntity(EntityType entityType) {
        drawSpawnArea = true;
        if (entityType == EntityType.ARCHER_ARMY) {
            for (int i = 0; i < 2; i++)
                ghostEntityViews.add(new EntityView(stage, true, EntityType.ARCHER, null));
        }
        else if (entityType == EntityType.SKELETON_ARMY) {
            for (int i = 0; i < 15; i++)
                ghostEntityViews.add(new EntityView(stage, true, EntityType.SKELETON, null));
        }
        else ghostEntityViews.add(new EntityView(stage, true, entityType, null));
    }

    public void hideGhostEntity() {
        drawSpawnArea = false;
        ghostEntityViews.forEach(EntityView::dispose);
        ghostEntityViews.clear();
    }
}
