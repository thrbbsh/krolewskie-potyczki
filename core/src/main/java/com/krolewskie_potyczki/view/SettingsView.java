package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.krolewskie_potyczki.AudioManager;
import com.krolewskie_potyczki.controller.SettingsController;
import com.krolewskie_potyczki.model.config.GameConfig;

public class SettingsView implements Disposable {
    public static final float SCREEN_WIDTH  = GameConfig.getInstance().getZonePointsConstantsConfig().screenWidth;
    public static final float SCREEN_HEIGHT = GameConfig.getInstance().getZonePointsConstantsConfig().screenHeight;

    private final Stage stage;
    private final Skin skin;
    private final Preferences prefs;

    private Slider volumeSlider;
    private ButtonGroup<TextButton> difficultyGroup;
    private TextButton menuButton;

    public SettingsView() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        skin  = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));
        prefs = Gdx.app.getPreferences("MyGameSettings");

        createUI();
    }

    private Label createLabel(String text, float scale) {
        Label lbl = new Label(text, skin);
        lbl.setFontScale(scale);
        return lbl;
    }

    private void createUI() {
        float savedVolume = prefs.getFloat("musicVolume",0.5f);
        int savedDiff = prefs.getInteger("difficulty",2);

        Label settingsLabel = createLabel("SETTINGS", 3f);

        Label volumeLabel = createLabel("Music volume:", 1.5f);
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volumeSlider.setValue(savedVolume);

        Label diffLabel = createLabel("Difficulty Level:", 1.5f);
        TextButton easy = new TextButton("Easy", skin);
        TextButton medium = new TextButton("Medium", skin);
        TextButton hard = new TextButton("Hard", skin);

        difficultyGroup = new ButtonGroup<>(easy, medium, hard);
        difficultyGroup.setMaxCheckCount(1);
        difficultyGroup.setMinCheckCount(1);
        difficultyGroup.setUncheckLast(true);
        difficultyGroup.getButtons().get(savedDiff - 1).setChecked(true);

        for (int i = 0; i < 3; i++) {
            TextButton btn = difficultyGroup.getButtons().get(i);
            btn.setColor(i == (savedDiff - 1) ? Color.WHITE : new Color(1f, 1f, 1f, 0.4f));
        }

        ChangeListener difficultyListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int selected = difficultyGroup.getCheckedIndex() + 1;
                prefs.putInteger("difficulty", selected).flush();
                for (int j = 0; j < difficultyGroup.getButtons().size; j++) {
                    TextButton btn = difficultyGroup.getButtons().get(j);
                    btn.setColor(j == (selected - 1) ? Color.WHITE : new Color(1f, 1f, 1f, 0.4f));
                }
            }
        };

        easy.addListener(difficultyListener);
        medium.addListener(difficultyListener);
        hard.addListener(difficultyListener);

        menuButton = new TextButton("Back to menu", skin);
        menuButton.getLabel().setFontScale(2f);

        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.add(settingsLabel).padTop(100f).row();
        table.add(volumeLabel).padTop(50f).row();
        table.add(volumeSlider).width(350f).height(80f).padTop(20f).row();
        table.add(diffLabel).padTop(50f).row();
        table.add(easy).padTop(30f).row();
        table.add(medium).padTop(10f).row();
        table.add(hard).padTop(10f).row();
        table.add(menuButton).size(525f, 120f).padTop(100f).row();

        stage.addActor(table);
    }

    public void setController(SettingsController controller) {
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.onMenuClicked();
            }
        });
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.onVolumeChanged(volumeSlider.getValue());
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

    public void pause() { }

    public void resume() { }

    public void hide() {
        if (AudioManager.inst().menuMusicIsPlaying())
            AudioManager.inst().pauseMenuMusic();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
