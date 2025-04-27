package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Entity;
import com.krolewskie_potyczki.model.SquareUnit;
import com.krolewskie_potyczki.model.Tower;

import javax.swing.*;
import java.awt.*;

public class EntityView implements Disposable {
    private Texture texture;
    private SpriteBatch batch;
    private Entity entity;

    public EntityView(Entity entity) {
        this.entity = entity;
        if (entity instanceof Tower) {
            if (entity.getIsPlayersEntity()) texture = new Texture("assets/images/playerTower.png");
            else texture = new Texture("assets/images/botTower.png");
        }
        else if (entity instanceof SquareUnit) {
            if (entity.getIsPlayersEntity()) texture = new Texture("assets/images/playerSquareUnit.png");
            else texture = new Texture("assets/images/botSquareUnit.png");
        }
        batch = new SpriteBatch();
    }

    public void render(float delta) {
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
