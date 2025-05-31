package com.krolewskie_potyczki.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.model.config.EntityType;

public class EntityView implements Disposable {
    private final Texture texture;
    private final SpriteBatch batch;
    private final Stage stage;
    private final ShapeRenderer shapeRenderer;
    private final float totalHP;

    private Vector2 pos;
    private float HP;

    public EntityView(Stage stage, boolean isPlayers, EntityType entityType, float totalHP) {
        shapeRenderer = new ShapeRenderer();
        this.stage = stage;
        this.totalHP = totalHP;

        String path = String.format("skins/%s/%s%s.png",
            isPlayers ? "player" : "bot",
            isPlayers ? "player" : "bot",
            entityType
        );
        texture = new Texture(path);
        batch = new SpriteBatch();
    }

    public void receivePackage(Vector2 pos, float HP) {
        this.pos = pos;
        this.HP = HP;
    }

    public void render(float ignoredDelta) {
        batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        batch.begin();
        batch.draw(texture, pos.x - texture.getWidth() / 2f, pos.y - texture.getHeight() / 2f);
        batch.end();
        drawLifeBar();
    }

    private void drawLifeBar() {
        float x = pos.x;
        float y = pos.y;
        float height = texture.getHeight();
        float width = texture.getWidth();
        shapeRenderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        float lifebarX = x - (100 - width) / 2 - width / 2f, lifebarY = y + height - height / 2f + 10;
        shapeRenderer.rect(lifebarX, lifebarY, 100, 25);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(lifebarX + 5, lifebarY + 5, 90, 15);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(lifebarX + 5, lifebarY + 5, 90 * HP / totalHP, 15);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }
}
