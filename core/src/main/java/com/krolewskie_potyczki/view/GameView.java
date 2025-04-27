package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Entity;
import com.krolewskie_potyczki.model.EntityType;

import javax.swing.event.ChangeEvent;

public class GameView implements Disposable {
    private ArenaView arenaView;
    private TextButton spawnBtn;
    private Skin skin;
    private Stage stage;
    private GameController controller;
    private Arena arena;

    public GameView(Arena arena, GameController controller) {
        this.arena = arena;
        arenaView = new ArenaView(arena);
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/skins/craftacular-ui.json"));
        TextButton spawnButton = new TextButton("Spawn square", skin);
        spawnButton.setPosition(10, 10);
        spawnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Entity e = controller.createEntity(EntityType.SQUARE, true,100, 100);
                arenaView.addEntityView(new EntityView(e));
            }
        });
        stage.addActor(spawnButton);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        arena.update();
        arenaView.render(delta);
        stage.act(delta);
        stage.draw();
    }

    public void show() {
        arenaView.show();
        Gdx.input.setInputProcessor(stage);
    }

    public void resize(int width, int height) {
        arenaView.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        arenaView.dispose();
        stage.dispose();
        skin.dispose();
    }

    public void pause() { }
    public void resume() { }
    public void hide() { }
}
