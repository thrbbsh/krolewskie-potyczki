package com.krolewskie_potyczki.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;

public class EntityView implements Disposable {
    private final Texture texture;
    private final SpriteBatch batch;
    private final Stage stage;
    private final ShapeRenderer shapeRenderer;
    private final Float totalHP;

    private final Sprite sprite;
    private Float HP;

    public EntityView(Stage stage, TeamType teamType, EntityType entityType, Float totalHP) {
        shapeRenderer = new ShapeRenderer();
        this.stage = stage;
        this.totalHP = totalHP;

        String path = String.format("skins/%s/%s%s.png",
            teamType == TeamType.PLAYER ? "player" : "bot",
            teamType == TeamType.PLAYER ? "player" : "bot",
            entityType
        );

        texture = new Texture(path);
        sprite = new Sprite(texture);
        sprite.setOriginCenter();
        batch = new SpriteBatch();
    }

    public void receivePackage(Vector2 pos, Float HP) {
        sprite.setCenter(pos.x, pos.y);
        this.HP = HP;
    }

    public void render(float ignoredDelta) {
        batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();
        if (HP != null && totalHP != null)
            drawLifeBar();
    }

    private void drawLifeBar() {
        float width  = sprite.getWidth();
        float height = sprite.getHeight();

        float barWidth  = width * 0.8f;
        float barHeight = Math.max(height * 0.1f, 5f);

        float xOffset = (width - barWidth) / 2f;
        float yOffset = height + 5f;

        float lifebarX = sprite.getX() + xOffset;
        float lifebarY = sprite.getY() + yOffset;

        float border = Math.min(10f, barHeight * 0.3f);

        shapeRenderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(
            lifebarX - border,
            lifebarY - border,
            barWidth + 2 * border,
            barHeight + 2 * border
        );

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(lifebarX, lifebarY, barWidth, barHeight);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(lifebarX, lifebarY, barWidth * (HP / totalHP), barHeight);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }

    public void setGhost() {
        sprite.setColor(1f, 1f, 1f, 0.3f);
    }
}
