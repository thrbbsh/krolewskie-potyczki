package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Entity;
import com.krolewskie_potyczki.model.EntityType;

import java.util.HashMap;
import java.util.Map;

public class    ArenaView implements Disposable {
    private final Map<Entity, EntityView> entityViews;
    private final CardView[] cardView;

    public ArenaView(Arena arena, Stage stage, GameController controller) {
        Texture bgTexture = new Texture(Gdx.files.internal("images/background/game/background.png"));
        Image bgImage = new Image(bgTexture);
        bgImage.setFillParent(true);
        stage.addActor(bgImage);
        cardView = new CardView[4];
        cardView[0] = new CardView(EntityType.SQUARE, 650, 22.5f, stage, arena, controller, this);
        cardView[1] = new CardView(EntityType.TRIANGLE, 820, 22.5f, stage, arena, controller, this);
        cardView[2] = new CardView(null, 990, 22.5f, stage, arena, controller, this);
        cardView[3] = new CardView(null, 1160, 22.5f, stage, arena, controller, this);
        entityViews = new HashMap<>();
        for (Entity entity: arena.getActiveEntities()) {
            addEntity(entity, stage);
        }
    }

    public void render(float delta) {
        for (int i = 0; i < 4; i++)
            cardView[i].render(delta);
        for (EntityView entityView: entityViews.values())
            entityView.render(delta);
    }

    public void show() {
        for (int i = 0; i < 4; i++)
            cardView[i].show();
        for (EntityView entityView: entityViews.values())
            entityView.show();
    }

    public void resize(int width, int height) {
        for (int i = 0; i < 4; i++)
            cardView[i].resize(width, height);
        for (EntityView entityView: entityViews.values())
            entityView.resize(width, height);
    }

    @Override
    public void dispose() {
        for (int i = 0; i < 4; i++)
            cardView[i].dispose();
        for (EntityView entityView: entityViews.values())
            entityView.dispose();
    }

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
