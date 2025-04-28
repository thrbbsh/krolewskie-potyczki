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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Entity;
import com.krolewskie_potyczki.model.EntityType;

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
        stage = new Stage(new FitViewport(1920, 1080));
        arenaView = new ArenaView(arena, stage);
        pauseStage = new Stage(new FitViewport(1920, 1080));
        skin = new Skin(Gdx.files.internal("skins/craftacular-ui.json"));

        TextButton spawnSquareButton = new TextButton("Spawn SquareUnit", skin);
        spawnSquareButton.setPosition(10, 10);
        spawnSquareButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (arena.getPlayerElixir() >= EntityType.SQUARE.getElixirCost()) {
                    Entity e = controller.createEntity(EntityType.SQUARE, true, 100, 100);
                    arenaView.addEntityView(new EntityView(e, stage));
                    arena.subtractElixir(EntityType.SQUARE.getElixirCost());
                }
            }
        });

        TextButton spawnTriangleButton = new TextButton("Spawn TriangleUnit", skin);
        spawnTriangleButton.setPosition(500, 10);
        spawnTriangleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (arena.getPlayerElixir() >= EntityType.TRIANGLE.getElixirCost()) {
                    Entity e = controller.createEntity(EntityType.TRIANGLE, true, 100, 900);
                    arenaView.addEntityView(new EntityView(e, stage));
                    arena.subtractElixir(EntityType.TRIANGLE.getElixirCost());
                }
            }
        });

        TextButton pauseButton = new TextButton("Pause", skin);
        pauseButton.setPosition(10, 1000);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.input.setInputProcessor(pauseStage);
                controller.onPauseClicked();
            }
        });

        currentElixirLabel = new Label("Current elixir: " + arena.getPlayerElixir() + "/" + arena.getMaxElixir(), skin);
        currentElixirLabel.setPosition(1500, 10);

        timerLabel = new Label("Time left: " + arena.getFormattedTimeLeft(), skin);
        timerLabel.setPosition(1600, 1000);

        stage.addActor(currentElixirLabel);
        stage.addActor(timerLabel);
        stage.addActor(spawnSquareButton);
        stage.addActor(spawnTriangleButton);
        stage.addActor(pauseButton);

        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.setPosition((1920 - resumeButton.getWidth()) / 2, (1080 - resumeButton.getHeight()) / 2 + 35);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.input.setInputProcessor(stage);
                controller.onResumeClicked();
            }
        });

        TextButton menuButton = new TextButton("Menu", skin);
        menuButton.setPosition((1920 - resumeButton.getWidth()) / 2, (1080 - resumeButton.getHeight()) / 2 - 35);
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
