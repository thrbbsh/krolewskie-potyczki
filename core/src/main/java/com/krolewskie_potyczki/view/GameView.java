package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Entity;
import com.krolewskie_potyczki.model.EntityType;

import java.util.ArrayList;

public class GameView implements Disposable {
    private final ArenaView arenaView;
    private final Skin skin;
    private final Stage stage;
    private final Stage pauseStage;
    private final Stage endStage;
    private final Label currentElixirLabel;
    private final Label timerLabel;
    private final Label endLabel;
    private boolean endProcessed = false;

    private final GameController controller;
    private final Arena arena;

    public GameView(Arena arena, GameController gc) {
        this.arena = arena;
        this.controller = gc;

        stage = new Stage(new FitViewport(1920, 1080));
        skin = new Skin(Gdx.files.internal("skins/craftacular-ui.json"));
        arenaView = new ArenaView(arena, stage);
        pauseStage = new Stage(new FitViewport(1920, 1080));
        endStage = new Stage(new FitViewport(1920, 1080));

        TextButton spawnSquareButton = new TextButton("Spawn SquareUnit", skin);
        spawnSquareButton.setPosition(10, 10);
        spawnSquareButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (arena.getPlayerElixir() >= EntityType.SQUARE.getElixirCost()) {
                    Entity e = controller.createEntity(EntityType.SQUARE, true, 100, 100);
                    arenaView.addEntity(e, stage);
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

        currentElixirLabel = new Label(
            "Current elixir: " + arena.getPlayerElixir() + "/" + arena.getMaxElixir(),
            skin
        );
        currentElixirLabel.setPosition(1500, 10);

        timerLabel = new Label(
            "Time left: " + arena.getFormattedTimeLeft(),
            skin
        );
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

        TextButton menuBtn = new TextButton("Menu", skin);
        menuBtn.setPosition(
            (1920 - menuBtn.getWidth()) / 2,
            (1080 - menuBtn.getHeight()) / 2 - 35
        );
        menuBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                controller.onMenuClicked();
            }
        });

        pauseStage.addActor(resumeButton);
        pauseStage.addActor(menuBtn);

        Table endTable = new Table();
        endTable.setFillParent(true);
        Label.LabelStyle ls = new Label.LabelStyle(skin.getFont("font"), Color.WHITE);

        endLabel = new Label("", ls);
        endLabel.setFontScale(3f);

        TextButton endMenuBtn = new TextButton("Menu", skin);
        endMenuBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                controller.onMenuClicked();
            }
        });

        endTable.center();
        endTable.add(endLabel).padBottom(50).row();
        endTable.add(endMenuBtn).size(300, 112);

        endStage.addActor(endTable);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 

        if (controller.isEnded()) {
            if (!endProcessed) {
                boolean pDead = arena.isPlayerTowerDestroyed();
                boolean eDead = arena.isEnemyTowerDestroyed();
                String text;
                if (eDead && !pDead) {
                    text = "You Win!";
                } else if (pDead && !eDead) {
                    text = "You Lose...";
                } else {
                    float pHP = arena.getPlayerTowerHP();
                    float eHP = arena.getEnemyTowerHP();
                    text = (eHP > pHP) ? "You Lose..." : "You Win!";
                }
                endLabel.setText(text);
                endProcessed = true;
                Gdx.input.setInputProcessor(endStage);
            }
            endStage.act(delta);
            endStage.draw();
            return;
        }

        if (!controller.isPaused()) {
            ArrayList<Entity> toRemove = arena.update(delta);
            for (Entity e : toRemove) {
                arena.removeEntity(e);
                arenaView.removeEntity(e);
            }

            boolean pAlive = !arena.isPlayerTowerDestroyed();
            boolean eAlive = !arena.isEnemyTowerDestroyed();
            if (!pAlive || !eAlive || arena.getTimeLeft() <= 0f) {
                controller.endOfMatch();
            } else {
                currentElixirLabel.setText(
                    "Current elixir: " + arena.getPlayerElixir() + "/" + arena.getMaxElixir()
                );
                timerLabel.setText(
                    "Time left: " + arena.getFormattedTimeLeft()
                );
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

    public void resize(int w, int h) {
        arenaView.resize(w, h);
        stage.getViewport().update(w, h, true);
        pauseStage.getViewport().update(w, h, true);
        endStage.getViewport().update(w, h, true);
    }

    @Override
    public void dispose() {
        arenaView.dispose();
        stage.dispose();
        pauseStage.dispose();
        endStage.dispose();
        skin.dispose();
    }

    public void pause()  { }
    public void resume() { }
    public void hide()   { }
}
