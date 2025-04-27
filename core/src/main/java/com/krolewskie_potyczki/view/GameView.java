package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Skin skin;
    private Stage stage;
    private GameController controller;
    private Arena arena;
    private Label currentElixirLabel;
    private Label timerLabel;
    private Stage pauseStage;

    public GameView(Arena arena, GameController gc) {
        this.controller = gc;
        this.arena = arena;
        arenaView = new ArenaView(arena);
        stage = new Stage();
        pauseStage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/skins/craftacular-ui.json"));

        TextButton spawnButton = new TextButton("Spawn square", skin);
        spawnButton.setPosition(10, 10);
        spawnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (arena.getPlayerElixir() >= EntityType.SQUARE.getElixirCost()) {
                    Entity e = controller.createEntity(EntityType.SQUARE, true, 100, 100);
                    arenaView.addEntityView(new EntityView(e));
                    arena.subtractElixir(EntityType.SQUARE.getElixirCost());
                }
            }
        });

        TextButton pauseButton = new TextButton("Pause", skin);
        pauseButton.setPosition(10, 1300);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.input.setInputProcessor(pauseStage);
                controller.onPauseClicked();
            }
        });

        currentElixirLabel = new Label("Current elixir: " + arena.getPlayerElixir() + "/" + arena.getMaxElixir(), skin);
        currentElixirLabel.setPosition(1900, 10);
        currentElixirLabel.setFontScale(1.5F);

        timerLabel = new Label("Time left: " + arena.getFormattedTimeLeft(), skin);
        timerLabel.setPosition(1900, 1300);
        timerLabel.setFontScale(1.5F);

        stage.addActor(currentElixirLabel);
        stage.addActor(timerLabel);
        stage.addActor(spawnButton);
        stage.addActor(pauseButton);

        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.setPosition(1120, 780);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.input.setInputProcessor(stage);
                controller.onResumeClicked();
            }
        });

        TextButton menuButton = new TextButton("Menu", skin);
        menuButton.setPosition(1120, 700);
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                controller.onMenuClicked();
            }
        });

        pauseStage.addActor(resumeButton);
        pauseStage.addActor(menuButton);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!controller.isEnded()) {
            if (!controller.isPaused()) {
                arena.update(delta);
                if (arena.getTimeLeft() < 0) {
                    controller.endOfMatch();
                } else {
                    currentElixirLabel.setText("Current elixir: " + arena.getPlayerElixir() + "/" + arena.getMaxElixir());
                    timerLabel.setText("Time left: " + arena.getFormattedTimeLeft());
                }
            }
        }
        arenaView.render(delta);
        stage.act(delta);
        stage.draw();
        if (controller.isPaused()) {
            pauseStage.act(delta);
            pauseStage.draw();
        }
    }

    public void show() {
        arenaView.show();
        Gdx.input.setInputProcessor(stage);
    }

    public void resize(int width, int height) {
        arenaView.resize(width, height);
        stage.getViewport().update(width, height, true);
        pauseStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        arenaView.dispose();
        stage.dispose();
        pauseStage.dispose();
        skin.dispose();
    }

    public void pause() { }
    public void resume() { }
    public void hide() { }
}
