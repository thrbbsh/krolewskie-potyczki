package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.krolewskie_potyczki.AudioManager;
import com.krolewskie_potyczki.controller.SettingsController;
import com.krolewskie_potyczki.model.config.GameConfig;

public class SettingsView implements Disposable {
    public static final float SCREEN_WIDTH = GameConfig.getInstance().getZonePointsConstantsConfig().screenWidth;
    public static final float SCREEN_HEIGHT = GameConfig.getInstance().getZonePointsConstantsConfig().screenHeight;

    private final Stage stage;
    private final Skin skin;

    private TextButton menuButton;
    private Slider volumeSlider;
    private final Preferences prefs;
    private final float savedVolume;
    private int difficulty;

    public SettingsView() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));

        prefs = Gdx.app.getPreferences("MyGameSettings");
        savedVolume = prefs.getFloat("musicVolume", 0.5f);
        difficulty = prefs.getInteger("difficulty", 2);

        createUI();
    }

    private void createUI() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.getFont("font"), Color.WHITE);
        Label settingsLabel = new Label("SETTINGS", labelStyle);
        settingsLabel.setFontScale(3f);

        Label menuVolumeLabel = new Label("Music volume:", skin);
        menuVolumeLabel.setFontScale(1.5f);

        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volumeSlider.setValue(savedVolume);

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float vol = volumeSlider.getValue();
                AudioManager.inst().setVolume(volumeSlider.getValue());
                prefs.putFloat("musicVolume", vol);
                prefs.flush();
            }
        });

        Label difficultyLevelLabel = new Label("Difficulty Level:", skin);
        difficultyLevelLabel.setFontScale(1.5f);

        TextButton easyOption = new TextButton("Easy", skin);
        if (difficulty != 1) easyOption.setColor(1f, 1f, 1f, 0.4f);
        TextButton mediumOption = new TextButton("Medium", skin);
        if (difficulty != 2) mediumOption.setColor(1f, 1f, 1f, 0.4f);
        TextButton hardOption = new TextButton("Hard", skin);
        if (difficulty != 3) hardOption.setColor(1f, 1f, 1f, 0.4f);

        ButtonGroup<TextButton> group = new ButtonGroup<>(easyOption, mediumOption, hardOption);
        group.setMaxCheckCount(1);
        group.setMinCheckCount(1);
        group.setUncheckLast(true);

        ChangeListener updateVisuals = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (int i = 0; i < group.getButtons().size; i++) {
                    TextButton btn = group.getButtons().get(i);
                    if (btn.isChecked()) {
                        btn.setColor(1f, 1f, 1f, 1f);
                        difficulty = i + 1;
                        prefs.putInteger("difficulty", difficulty);
                    } else {
                        btn.setColor(1f, 1f, 1f, 0.4f);
                    }
                    prefs.flush();
                }
            }
        };

        easyOption.addListener(updateVisuals);
        mediumOption.addListener(updateVisuals);
        hardOption.addListener(updateVisuals);

        menuButton = new TextButton("Back to menu", skin);
        menuButton.getLabel().setFontScale(2f);

        Table menu = new Table();
        menu.setFillParent(true);

        menu.top();
        menu.add(settingsLabel).padTop(100).row();
        menu.add(menuVolumeLabel).padTop(50).row();
        menu.add(volumeSlider).width(350).height(80).padTop(20).row();
        menu.add(difficultyLevelLabel).padTop(50).row();
        menu.add(easyOption).padTop(30).row();
        menu.add(mediumOption).padTop(10).row();
        menu.add(hardOption).padTop(10).row();
        menu.add(menuButton).size(525, 120).padTop(100).row();

        stage.addActor(menu);
    }

    public void setController(SettingsController controller) {
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.onMenuClicked();
            }
        });
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
        AudioManager.inst().playMenuMusic();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.7f, 1f, 1f);
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
        skin.dispose();
    }

    public void pause() {
    }

    public void resume() {
    }

    public void hide() {
        if (AudioManager.inst().menuMusicIsPlaying()) AudioManager.inst().pauseMenuMusic();
    }
}
