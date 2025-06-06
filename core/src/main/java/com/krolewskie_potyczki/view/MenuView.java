package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.krolewskie_potyczki.controller.MenuController;

public class MenuView implements Disposable {
    private final Stage stage;
    private final Skin skin;

    private Music menuMusic;
    private TextButton startButton;
    private TextButton exitButton;

    public MenuView() {
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));

        setupMusic();
        createUI();
    }

    private void setupMusic() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/MenuSoundtrack.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.1f);
        menuMusic.play();
    }

    private void createUI() {
        Texture crownTexture = new Texture(Gdx.files.internal("images/background/menu/crown.png"));
        Image crownImage = new Image(crownTexture);

        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.getFont("font"), Color.WHITE);
        Label titleLabel = new Label("KROLEWSKIE POTYCZKI", labelStyle);
        titleLabel.setFontScale(3f);

        startButton = new TextButton("Start", skin);
        startButton.getLabel().setFontScale(2f);

        exitButton = new TextButton("Exit", skin);
        exitButton.getLabel().setFontScale(2f);

        Table menu = new Table();
        menu.setFillParent(true);

        menu.top();
        menu.add(crownImage).size(360, 250).padTop(20).row();
        menu.add(titleLabel).padTop(30).row();
        menu.add(startButton).size(525, 120).padTop(50).row();
        menu.add(exitButton).padTop(20).size(525, 120);

        stage.addActor(menu);
    }

    public void setController(MenuController controller) {
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.onPlayClicked();
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.onExitClicked();
            }
        });
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
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
        menuMusic.dispose();
    }

    public void pause() {
    }

    public void resume() {
    }

    public void hide() {
        if (menuMusic != null && menuMusic.isPlaying()) menuMusic.stop();
    }
}
