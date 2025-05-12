package com.krolewskie_potyczki.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.model.*;

public class EntityView implements Disposable {
    private final Texture texture;
    private final Texture yellowPixelTexture;
    private final Texture whitePixelTexture;
    private final Texture redPixelTexture;
    private final SpriteBatch batch;
    private final Entity entity;
    private final Stage stage;

    public static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public EntityView(Entity entity, Stage stage) {
        yellowPixelTexture = new Texture("images/entities/yellowPixel.png");
        whitePixelTexture = new Texture("images/entities/whitePixel.png");
        redPixelTexture = new Texture("images/entities/redPixel.png");
        this.stage = stage;
        this.entity = entity;
        String internalWay = "skins/";
        if (entity.getIsPlayersEntity()) internalWay += "player/player";
            else internalWay += "bot/bot";
        internalWay += capitalize(entity.getType().toString().toLowerCase());
        if (entity instanceof Unit) internalWay += "Unit";
        internalWay += ".png";
        texture = new Texture(internalWay);
        batch = new SpriteBatch();
    }

    public void render(float ignoredDelta) {
        float x = entity.getX(), y = entity.getY(), height = texture.getHeight(), width = texture.getWidth();
        batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        batch.begin();
        batch.draw(texture, x, y);
        batch.draw(yellowPixelTexture, x - (100 - width) / 2, y + height + 10, 100, 25);
        batch.draw(whitePixelTexture, x - (100 - width) / 2 + 5, y + height + 15, 90, 15);
        batch.draw(redPixelTexture, x - (100 - width) / 2 + 5, y + height + 15, 90 * entity.getHP() / entity.getType().getTotalHP(), 15);
        batch.end();
    }

    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }
}
