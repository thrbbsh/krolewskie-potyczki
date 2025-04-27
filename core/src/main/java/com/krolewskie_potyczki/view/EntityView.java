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
import com.krolewskie_potyczki.model.Tower;

import javax.swing.*;
import java.awt.*;

public class EntityView implements Disposable {
    private Texture texture;
    private SpriteBatch batch;
    private Stage stage;
    private Entity entity;

    public EntityView(Entity entity) {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        this.entity = entity;
        //texture = new Texture("assets/images/playerTower.png");
    }

    private Image loadImageBasedOnEntityType(Entity entity) {
        if (entity instanceof Tower) {
            if (entity.getIsPlayersEntity()) return new ImageIcon("assets/images/playerTower.png").getImage();
                else return new ImageIcon("assets/images/botTower.png").getImage();
        }
        return null;  // по умолчанию, если не определено
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, entity.getX(), entity.getY());
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*
        batch.begin();
        batch.draw(background,
            0, 0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());
        batch.end();
        */

        stage.act(delta);
        stage.draw();
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        texture.dispose();
    }

    public void pause() { }
    public void resume() { }
    public void hide() { }
}
