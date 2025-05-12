package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Entity;

public class GameView implements Disposable {

    private final Skin skin;
    private final Stage gameStage;
    private final Stage pauseStage;
    private final Stage endStage;
    private final Label currentElixirLabel;
    private final Label timerLabel;
    private final Label endLabel;
    private final ShapeRenderer shapeRenderer;
    private final ArenaView arenaView;

    private final GameController controller;

    public GameView(Arena arena, GameController gc) {
        this.controller = gc;
        shapeRenderer = new ShapeRenderer();

        gameStage = new Stage(new FitViewport(1920, 1080));
        pauseStage = new Stage(new FitViewport(1920, 1080));
        endStage = new Stage(new FitViewport(1920, 1080));
        arenaView = new ArenaView(arena, gameStage);

        CardClickListener listener = (card) -> {
            if (controller.getPlayerElixir() >= card.getElixirCost()) {
                Entity e = controller.spawnEntity(card.getEntityType(), true, 400, 900);
                arenaView.addEntityView(e);
                controller.spendElixir(card.getElixirCost());
            }
        };

        arenaView.setListener(listener);

        skin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));

        TextButton pauseButton = new TextButton("Pause", skin);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.input.setInputProcessor(pauseStage);
                controller.onPauseClicked();
            }
        });

        currentElixirLabel = new Label(
            "Current elixir:\n" + controller.getFormattedPlayerElixir() + "/" + controller.getMaxElixir(),
            skin
        );
        currentElixirLabel.setWrap(true);
        currentElixirLabel.setAlignment(Align.center);

        timerLabel = new Label(
            "Time left:\n" + controller.getFormattedTimeLeft(),
            skin
        );
        timerLabel.setWrap(true);
        timerLabel.setAlignment(Align.center);

        Table topTable = new Table();
        topTable.setFillParent(true);
        topTable.top().left();
        gameStage.addActor(topTable);

        topTable.add(pauseButton)
            .size(250, 60)
            .padTop(6)
            .row();

        topTable.add(timerLabel)
            .padTop(7)
            .left()
            .width(300);


        Table bottomTable = new Table();
        bottomTable.setFillParent(true);
        bottomTable.bottom().left();
        gameStage.addActor(bottomTable);

        bottomTable.add().expandX();
        bottomTable.add(currentElixirLabel).padRight(350).padBottom(100).right().width(300).padLeft(500);

        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.input.setInputProcessor(gameStage);
                controller.onResumeClicked();
            }
        });

        TextButton menuBtn = new TextButton("Menu", skin);
        menuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.onMenuClicked();
            }
        });

        pauseStage.addActor(resumeButton);
        pauseStage.addActor(menuBtn);

        Table pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.center();
        pauseStage.addActor(pauseTable);

        pauseTable.add(resumeButton)
            .size(350, 80)
            .padBottom(20)
            .row();

        pauseTable.add(menuBtn)
            .size(350, 80);

        Table endTable = new Table();
        endTable.setFillParent(true);
        Label.LabelStyle ls = new Label.LabelStyle(skin.getFont("font"), Color.WHITE);

        endLabel = new Label("", ls);
        endLabel.setFontScale(3f);

        TextButton endMenuBtn = new TextButton("Menu", skin);
        endMenuBtn.getLabel().setFontScale(2f);
        endMenuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.onMenuClicked();
            }
        });

        endTable.center();
        endTable.add(endLabel).padBottom(50).row();
        endTable.add(endMenuBtn).size(525, 120);

        endStage.addActor(endTable);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (controller.isEnded()) {
            endLabel.setText(controller.getMatchResult());
            endStage.act(delta);
            endStage.draw();
            Gdx.input.setInputProcessor(endStage);
            return;
        }

        if (!controller.isPaused()) {
            controller.update(delta);
            currentElixirLabel.setText(
                "Current elixir: " + controller.getFormattedPlayerElixir() + "/" + controller.getMaxElixir()
            );
            timerLabel.setText(
                "Time left: " + controller.getFormattedTimeLeft()
            );

            gameStage.act(delta);
            gameStage.draw();
            arenaView.render(delta);
            shapeRenderer.setProjectionMatrix(gameStage.getViewport().getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(1300, 30, 240, 50);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(1305, 35, 230, 40);
            shapeRenderer.setColor(Color.PURPLE);
            shapeRenderer.rect(1305, 35, 230 * (controller.getFormattedPlayerElixir() / controller.getMaxElixir()), 40);
            shapeRenderer.end();
        }

        if (controller.isPaused()) {
            pauseStage.act(delta);
            pauseStage.draw();
        }
    }

    public void show() {
        Gdx.input.setInputProcessor(gameStage);
    }

    public void resize(int w, int h) {
        gameStage.getViewport().update(w, h, true);
        pauseStage.getViewport().update(w, h, true);
        endStage.getViewport().update(w, h, true);
    }

    @Override
    public void dispose() {
        gameStage.dispose();
        pauseStage.dispose();
        endStage.dispose();
        skin.dispose();
    }

    public void pause()  { }
    public void resume() { }
    public void hide()   { }
}
