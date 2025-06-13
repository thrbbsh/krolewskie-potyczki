package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
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

import com.krolewskie_potyczki.AudioManager;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Entity;
import java.util.List;

public class GameView implements Disposable {
    public static final float SCREEN_WIDTH = GameConfig.getInstance().getZonePointsConstantsConfig().screenWidth;
    public static final float SCREEN_HEIGHT = GameConfig.getInstance().getZonePointsConstantsConfig().screenHeight;

    private final Skin skin;
    public final Stage gameStage;
    private Label currentElixirLabel;
    private Label timerLabel;
    private Label doubleElixirLabel;

    private TextButton pauseButton;

    private final ShapeRenderer shapeRenderer;
    private final ArenaView arenaView;

    public GameView() {
        shapeRenderer = new ShapeRenderer();

        gameStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));

        arenaView = new ArenaView(gameStage);

        skin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));

        createUI();
    }

    private void createUI() {
        pauseButton = new TextButton("Pause", skin);

        currentElixirLabel = new Label(getFormattedPlayerElixir(0f, 0f), skin);
        currentElixirLabel.setWrap(true);
        currentElixirLabel.setAlignment(Align.center);

        timerLabel = new Label("Time left:\n" + getFormattedTimeLeft(0f), skin);
        timerLabel.setWrap(true);
        timerLabel.setAlignment(Align.center);

        doubleElixirLabel = new Label("60 seconds left\nx2 Elixir", skin);
        doubleElixirLabel.setWrap(true);
        doubleElixirLabel.setAlignment(Align.center);
        doubleElixirLabel.setSize(1000, 300);
        doubleElixirLabel.setFontScale(2f);
        doubleElixirLabel.setPosition(SCREEN_WIDTH / 2f - 500, SCREEN_HEIGHT / 2f - 100);

        Table topTable = new Table();
        topTable.setFillParent(true);
        topTable.top().left();
        gameStage.addActor(topTable);

        topTable.add(pauseButton).size(250, 60).padTop(6).row();
        topTable.add(timerLabel).padTop(7).left().width(300);
        topTable.row();

        Table bottomTable = new Table();
        bottomTable.setFillParent(true);
        bottomTable.bottom().left();

        gameStage.addActor(bottomTable);
        gameStage.addActor(doubleElixirLabel);

        bottomTable.add().expandX();
        bottomTable.add(currentElixirLabel).padRight(350).padBottom(100).right().width(300).padLeft(500);
    }

    public void setController(GameController controller) {
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                controller.onPauseClicked();
            }
        });
        gameStage.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                controller.onMapTouched(new Vector2(x, y));
                return true;
            }
        });
    }

    private String getFormattedPlayerElixir(float playerElixir, float maxElixir) {
        return String.format("Current elixir:\n %s / %s", Math.floor(playerElixir * 10) / 10, Math.floor(maxElixir * 10) / 10);
    }

    private String getFormattedTimeLeft(float timeLeft) {
        return String.format("%01d:%02d", (int) Math.floor(timeLeft / 60), (int) Math.floor(timeLeft % 60));
    }

    public void renderGame(float delta, float playerElixir, float maxElixir, float timeLeft, List<Entity> activeEntities) {
        Gdx.gl.glClearColor(0f, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        currentElixirLabel.setText(getFormattedPlayerElixir(playerElixir, maxElixir));
        timerLabel.setText("Time left: " + getFormattedTimeLeft(timeLeft));

        boolean showDouble = timeLeft >= 56.5f && timeLeft <= 60f;
        doubleElixirLabel.setVisible(showDouble);

        gameStage.act(delta);
        gameStage.draw();
        arenaView.sync(activeEntities);
        arenaView.render(delta);

        renderElixirBar(playerElixir, maxElixir);
    }

    private void renderElixirBar(float playerElixir, float maxElixir) {
        shapeRenderer.setProjectionMatrix(gameStage.getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(1300, 30, 240, 50);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(1305, 35, 230, 40);
        shapeRenderer.setColor(Color.PURPLE);
        shapeRenderer.rect(1305, 35, 230 * (playerElixir / maxElixir), 40);
        shapeRenderer.setColor(Color.BLACK);
        for (int i = 1; i <= 9; i++)
            shapeRenderer.rect(1305 + i * 23 - 2.5f, 35, 5, 40);
        shapeRenderer.end();
    }

    public void show() {
        Gdx.input.setInputProcessor(gameStage);
        AudioManager.inst().playGameMusic();
    }

    public void resize(int w, int h) {
        gameStage.getViewport().update(w, h, true);
    }

    @Override
    public void dispose() {
        gameStage.dispose();
        skin.dispose();
    }

    public void pause() {
        AudioManager.inst().pauseGameMusic();
    }

    public void resume() {
        Gdx.input.setInputProcessor(gameStage);
        AudioManager.inst().resumeGameMusic();
    }


    public void hide() { }

    public ArenaView getArenaView() {
        return arenaView;
    }
}
