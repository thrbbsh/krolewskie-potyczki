package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.unit.CompositeUnit;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ArenaView implements Disposable {
    private final Stage stage;
    private final Map<Entity, EntityView> entityViews;
    private final List<EntityView> ghostEntityViews = new ArrayList<>();
    private final SpriteBatch bgBatch;
    private final Texture bgTexture;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean drawSpawnArea = false;

    public static final float RIVER_X_START = GameConfig.getInstance().getZonePointsConstantsConfig().riverXStart;
    public static final float RIGHT_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().rightBorder;
    public static final float UP_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().upBorder;
    public static final float DOWN_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().downBorder;

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
            entityViews.computeIfAbsent(e, key -> new EntityView(stage, e.getTeamType(), e.getConfig().type, e.getConfig().totalHP))
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
        entityViews.entrySet().stream()
            .sorted(Comparator
                .comparing((Map.Entry<Entity, EntityView> e) -> e.getKey().getHitboxPos().y).reversed()
                .thenComparing(e -> e.getKey().getHitboxPos().x))
            .forEach(entry -> {
                Entity entity = entry.getKey();
                EntityView entityView = entry.getValue();
                entityView.receivePackage(entity.getViewPos(), entity.getHP());
                entityView.render(delta);
            });

        Vector2 touch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        stage.screenToStageCoordinates(touch);

        int count = ghostEntityViews.size();
        AtomicInteger idx = new AtomicInteger(0);

        ghostEntityViews.stream().peek(view -> {
            int i = idx.getAndIncrement();
            view.receivePackage(CompositeUnit.calculateOffsetPosition(touch.cpy(), i, count), null);
        }).forEach(view -> view.render(delta));

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
            RIVER_X_START, DOWN_BORDER,
            RIGHT_BORDER - RIVER_X_START, UP_BORDER - DOWN_BORDER
        );
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        for (EntityView entityView: entityViews.values())
            entityView.dispose();
        stage.dispose();
    }

    public void addGhost(EntityType entityType) {
        EntityView entityView = new EntityView(stage, TeamType.PLAYER, entityType, null);
        entityView.setGhost();
        ghostEntityViews.add(entityView);
    }

    public void clearGhost() {
        ghostEntityViews.forEach(EntityView::dispose);
        ghostEntityViews.clear();
    }

    public void setDrawSpawnArea(boolean drawSpawnArea) {
        this.drawSpawnArea = drawSpawnArea;
    }
}
