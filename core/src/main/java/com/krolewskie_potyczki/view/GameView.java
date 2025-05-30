package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.Arena;

public class GameView implements Disposable {

    private final Skin skin;
    private final Stage gameStage;
    private final Stage pauseStage;
    private final Label currentElixirLabel;
    private final Label timerLabel;
    private final ShapeRenderer shapeRenderer;
    private final ArenaView arenaView;
    private final Music gameMusic;

    private final GameController controller;

    public GameView(Arena arena, GameController gc) {
        this.controller = gc;
        shapeRenderer = new ShapeRenderer();

        gameStage = new Stage(new FitViewport(1920, 1080));
        pauseStage = new Stage(new FitViewport(1920, 1080));
        arenaView = new ArenaView(arena, gameStage);

        CardClickListener listener = (card) -> {
            if (card.getEntityType() == null)
                return;
            controller.onSpawnEntityClicked(arenaView.getCardView(card));
            arenaView.setSpawnArea(controller.getSelectedCardView() != null);
        };

        arenaView.setListener(listener);

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/GameSoundtrack.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.1f);
        gameMusic.play();

        gameStage.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (controller.onMapTouched(new Vector2(x, y))) {
                    arenaView.setSpawnArea(false);
                    return true;
                }
                return false;
            }
        });


        skin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));

        TextButton pauseButton = new TextButton("Pause", skin);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.input.setInputProcessor(pauseStage);
                controller.onPauseClicked();
                gameMusic.pause();
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
                gameMusic.play();
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
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
            renderElixirBar();
        }

        if (controller.isPaused()) {
            pauseStage.act(delta);
            pauseStage.draw();
        }
    }

    private void renderElixirBar() {
        shapeRenderer.setProjectionMatrix(gameStage.getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(1300, 30, 240, 50);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(1305, 35, 230, 40);
        shapeRenderer.setColor(Color.PURPLE);
        shapeRenderer.rect(1305, 35, 230 * (controller.getFormattedPlayerElixir() / controller.getMaxElixir()), 40);
        shapeRenderer.setColor(Color.BLACK);
        for (int i = 1; i <= 9; i++)
            shapeRenderer.rect(1305 + i * 23 - 2.5f, 35, 5, 40);
        shapeRenderer.end();
    }


    public void show() {
        Gdx.input.setInputProcessor(gameStage);
    }

    public void resize(int w, int h) {
        gameStage.getViewport().update(w, h, true);
        pauseStage.getViewport().update(w, h, true);
    }

    @Override
    public void dispose() {
        gameMusic.dispose();
        gameStage.dispose();
        pauseStage.dispose();
        skin.dispose();
    }

    public void pause()  { }
    public void resume() { }
    public void hide()   { }
}
