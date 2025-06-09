package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.config.GameConfig;

public class PauseView implements Disposable {
    public static final float SCREEN_WIDTH = GameConfig.getInstance().getZonePointsConstantsConfig().screenWidth;
    public static final float SCREEN_HEIGHT = GameConfig.getInstance().getZonePointsConstantsConfig().screenHeight;

    private final Stage stage;

    private final TextButton resumeButton;
    private final TextButton menuButton;

    public PauseView() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
        Skin skin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));

        resumeButton = new TextButton("Resume", skin);
        menuButton = new TextButton("Menu", skin);

        stage.addActor(resumeButton);
        stage.addActor(menuButton);

        Table pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.center();

        pauseTable.add(resumeButton).size(350, 80).padBottom(20).row();
        pauseTable.add(menuButton).size(350, 80);

        stage.addActor(pauseTable);
    }

    public void setController(GameController controller) {
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                controller.onResumeClicked();
            }
        });
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.onMenuClicked();
            }
        });
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void pause() {
    }

    public void resume() {
    }

    public void hide() {
    }
}
