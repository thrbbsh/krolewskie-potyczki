package com.krolewskie_potyczki.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.model.*;

public class EntityView implements Disposable {
    private final Texture texture;
    private final SpriteBatch batch;
    private final Entity entity;
    private final Stage stage;
    private final ShapeRenderer shapeRenderer;

    public EntityView(Entity entity, Stage stage) {
        shapeRenderer = new ShapeRenderer();
        this.stage = stage;
        this.entity = entity;

        String path = String.format("skins/%s/%s%s.png",
            entity.getIsPlayersEntity() ? "player" : "bot",
            entity.getIsPlayersEntity() ? "player" : "bot",
            entity.getConfig().type
        );
        texture = new Texture(path);
        batch = new SpriteBatch();
    }

    public void render(float ignoredDelta) {
        batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        batch.begin();
        Vector2 pos = entity.getPos();
        batch.draw(texture, pos.x - texture.getWidth() / 2f, pos.y - texture.getHeight() / 2f);
        batch.end();
        drawLifeBar();
    }

    private void drawLifeBar() {
        float x = entity.getPos().x;
        float y = entity.getPos().y;
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
        shapeRenderer.rect(lifebarX + 5, lifebarY + 5, 90 * entity.getHP() / entity.getConfig().totalHP, 15);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }
}
