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
    private SpriteBatch batch;
    private Entity entity;
    private Stage stage;

    public EntityView(Entity entity, Stage stage) {
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
        batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        batch.begin();
        batch.draw(texture, entity.getX(), entity.getY());
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
