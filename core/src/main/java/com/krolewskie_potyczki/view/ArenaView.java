package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Card;
import com.krolewskie_potyczki.model.Entity;
import com.krolewskie_potyczki.model.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaView implements Disposable {
    private final Stage stage;
    private final Map<Entity, EntityView> entityViews;
    private final CardView[] cardViews;
    private final SpriteBatch bgBatch;
    private final Texture bgTexture;
    private final Arena arena;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean drawSpawnArea = false;

    public ArenaView(Arena arena, Stage stage) {
        this.arena = arena;
        this.stage = stage;
        entityViews = new HashMap<>();
        bgBatch = new SpriteBatch();
        bgTexture = new Texture(Gdx.files.internal("images/background/game/background.png"));
        cardViews = new CardView[4];
    }

    public void setListener(CardClickListener listener) {
        for (int i = 0; i < cardViews.length; i++) {
            EntityType type = null;
            if (i == 0) type = EntityType.Square;
            if (i == 1) type = EntityType.Triangle;
            if (i == 2) type = EntityType.Tombstone;
            cardViews[i] = new CardView(type, 650 + i * 160, 22.5f, listener);
            cardViews[i].addToStage(stage);
        }
    }

    private void sync() {
        for (Entity e : arena.getActiveEntities()) {
            if (!entityViews.containsKey(e)) {
                EntityView ev = new EntityView(e, stage);
                entityViews.put(e, ev);
            }
        }
        List<Map.Entry<Entity, EntityView>> list = new ArrayList<>();
        for (Map.Entry<Entity, EntityView> e : entityViews.entrySet())
            if (!arena.getActiveEntities().contains(e.getKey())) list.add(e);
        for (Map.Entry<Entity, EntityView> e : list) {
            entityViews.remove(e.getKey());
        }
    }

    public void render(float delta) {
        sync();
        bgBatch.begin();
        bgBatch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        bgBatch.draw(bgTexture, 0, 0, stage.getWidth(), stage.getHeight());
        bgBatch.end();

        if (drawSpawnArea) {
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

        for (int i = 0; i < 4; i++)
            cardViews[i].render(delta);
        for (EntityView entityView: entityViews.values())
            entityView.render(delta);
        stage.act(delta);
        stage.draw();
        for (int i = 0; i < 4; i++)
            cardViews[i].render(delta);
    }

    @Override
    public void dispose() {
        for (int i = 0; i < 4; i++)
            cardViews[i].dispose();
        for (EntityView entityView: entityViews.values())
            entityView.dispose();
        stage.dispose();
    }

    public CardView getCardView(Card fCard) {
        for (CardView cardView : cardViews)
            if (cardView.getCard().equals(fCard))
                return cardView;
        return null;
    }

    public void setSpawnArea(boolean state) {
        drawSpawnArea = state;
    }
}
