package com.krolewskie_potyczki.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.model.Entity;
import com.krolewskie_potyczki.model.SquareUnit;
import com.krolewskie_potyczki.model.Tower;
import com.krolewskie_potyczki.model.TriangleUnit;

public class EntityView implements Disposable {
    private Texture texture;
    private Texture yellowPixelTexture;
    private Texture whitePixelTexture;
    private Texture redPixelTexture;
    private SpriteBatch batch;
    private Entity entity;
    private Stage stage;

    public EntityView(Entity entity, Stage stage) {
        yellowPixelTexture = new Texture("images/yellowPixel.png");
        whitePixelTexture = new Texture("images/whitePixel.png");
        redPixelTexture = new Texture("images/redPixel.png");
        this.stage = stage;
        this.entity = entity;
        if (entity instanceof Tower) {
            if (entity.getIsPlayersEntity()) texture = new Texture("images/playerTower.png");
            else texture = new Texture("images/botTower.png");
        }
        else if (entity instanceof SquareUnit) {
            if (entity.getIsPlayersEntity()) texture = new Texture("images/playerSquareUnit.png");
            else texture = new Texture("images/botSquareUnit.png");
        }
        else if (entity instanceof TriangleUnit) {
            if (entity.getIsPlayersEntity()) texture = new Texture("images/playerTriangleUnit.png");
            else texture = new Texture("images/botTriangleUnit.png");
        }
        batch = new SpriteBatch();
    }

    public void render(float delta) {
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

    public void show() {
    }

    public void resize(int width, int height) {
    }
}
