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
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.unit.CompositeUnit;

import java.util.*;

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

    private static final int ARCHER_ARMY_SIZE = GameConfig.getInstance().getCompositeUnitConstantsConfig().archerArmySize;
    private static final int SKELETON_ARMY_SIZE = GameConfig.getInstance().getCompositeUnitConstantsConfig().skeletonArmySize;

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
        for (int i = 0; i < ghostEntityViews.size(); i++) {
            EntityView entityView = ghostEntityViews.get(i);
            if (ghostEntityViews.size() == 1)
                entityView.receivePackage(new Vector2(touch.x, touch.y), null);
            else
                entityView.receivePackage(CompositeUnit.calculateOffsetPosition(new Vector2(touch.x, touch.y), i, ghostEntityViews.size()), null);
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

    public void showGhostEntity(EntityType entityType) {
        drawSpawnArea = true;
        if (entityType == EntityType.ARCHER_ARMY) {
            for (int i = 0; i < ARCHER_ARMY_SIZE; i++) {
                EntityView entityView = new EntityView(stage, TeamType.PLAYER, EntityType.ARCHER, null);
                ghostEntityViews.add(entityView);
                entityView.setGhost();
            }
        }
        else if (entityType == EntityType.SKELETON_ARMY) {
            for (int i = 0; i < SKELETON_ARMY_SIZE; i++) {
                EntityView entityView = new EntityView(stage, TeamType.PLAYER, EntityType.SKELETON, null);
                ghostEntityViews.add(entityView);
                entityView.setGhost();
            }
        }
        else {
            EntityView entityView = new EntityView(stage, TeamType.PLAYER, entityType, null);
            ghostEntityViews.add(entityView);
            entityView.setGhost();
        }
    }

    public void hideGhostEntity() {
        drawSpawnArea = false;
        ghostEntityViews.forEach(EntityView::dispose);
        ghostEntityViews.clear();
    }
}
