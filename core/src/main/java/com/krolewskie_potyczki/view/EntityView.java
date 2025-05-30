package com.krolewskie_potyczki.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

        String internalWay = "skins/";
        if (entity.getIsPlayersEntity()) internalWay += "player/player";
        else internalWay += "bot/bot";
        internalWay += entity.getConfig().type;
        internalWay += ".png";
        texture = new Texture(internalWay);
        batch = new SpriteBatch();
    }

    public void render(float ignoredDelta) {
        batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        batch.begin();
        batch.draw(texture, entity.getX() - texture.getWidth() / 2f, entity.getY() - texture.getHeight() / 2f);
        batch.end();
        drawLifeBar();
    }

    private void drawLifeBar() {
        float x = entity.getX(), y = entity.getY(), height = texture.getHeight(), width = texture.getWidth();
        shapeRenderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(x - (100 - width) / 2 - texture.getWidth() / 2f, y + height + 10 - texture.getHeight() / 2f, 100, 25);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x - (100 - width) / 2 + 5 - texture.getWidth() / 2f, y + height + 15 - texture.getHeight() / 2f, 90, 15);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x - (100 - width) / 2 + 5 - texture.getWidth() / 2f, y + height + 15 - texture.getHeight() / 2f, 90 * entity.getHP() / entity.getConfig().totalHP, 15);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }
}
