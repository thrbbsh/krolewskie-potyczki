package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.krolewskie_potyczki.AudioManager;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.config.GameConfig;

public class PauseView implements Disposable {
    public static final float SCREEN_WIDTH = GameConfig.getInstance().getZonePointsConstantsConfig().screenWidth;
    public static final float SCREEN_HEIGHT = GameConfig.getInstance().getZonePointsConstantsConfig().screenHeight;

    private final Stage stage;
    private final Skin skin;

    private final TextButton resumeButton;
    private final TextButton menuButton;

    private final Slider volumeSlider;
    private final Preferences prefs;
    private final float savedVolume;

    public PauseView() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
        skin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));

        resumeButton = new TextButton("Resume", skin);
        menuButton = new TextButton("Menu", skin);

        prefs = Gdx.app.getPreferences("MyGameSettings");
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        savedVolume = prefs.getFloat("musicVolume", 0.5f);

        createUI();
    }

    void createUI() {
        Label volumeLabel = new Label("Music volume:", skin);
        volumeSlider.setValue(savedVolume);

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float vol = volumeSlider.getValue();
                AudioManager.inst().setVolume(vol);
                prefs.putFloat("musicVolume", vol);
                prefs.flush();
            }
        });

        stage.addActor(resumeButton);
        stage.addActor(menuButton);

        Table pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.center();

        pauseTable.add(resumeButton).size(350, 80).row();
        pauseTable.add(menuButton).size(350, 80).padTop(20).row();
        pauseTable.add(volumeLabel).padTop(100).row();
        pauseTable.add(volumeSlider).width(350).height(60).padTop(20);

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
