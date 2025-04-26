package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.krolewskie_potyczki.Main;

public class MenuView implements Disposable {
    private Stage stage;
    private Skin skin;
    private Label titleLabel;
    private Music menuMusic;


    public MenuView(Main game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skins/craftacular-ui.json"));

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Soundtrack1.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(1f);
        menuMusic.play();


        Texture crownTexture = new Texture(Gdx.files.internal("crown.png"));
        Image crownImage = new Image(crownTexture);
        float desiredHeight = 250f;
        float desiredWidth = 360f;
        crownImage.setSize(desiredWidth, desiredHeight);
        crownImage.setScaling(Scaling.stretch);
        stage.addActor(crownImage);
        Viewport vp = stage.getViewport();
        crownImage.setPosition((vp.getWorldWidth()  - desiredWidth ) / 2f, (vp.getWorldHeight() - desiredHeight));


        Table table = new Table();
        table.setFillParent(true);
        table.top().center().padTop(crownImage.getHeight() + 10);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("font");
        Label titleLabel = new Label("KROLEWSKIE POTYCZKI", labelStyle);
        titleLabel.setFontScale(3f);

        ImageTextButton startButton = new ImageTextButton("Start", skin, "default");
        startButton.getLabel().setFontScale(4f);

        ImageTextButton exitButton = new ImageTextButton("Exit", skin, "default");
        exitButton.getLabel().setFontScale(4f);

        startButton.addListener(event -> {
            if (startButton.isPressed()) {
                System.out.println("Начать игру!");
            }
            return true;
        });

        exitButton.addListener(event -> {
            if (exitButton.isPressed()) {
                Gdx.app.exit();
            }
            return true;
        });

        table.add(titleLabel).center().padTop(-300).row();
        table.add(startButton).size(400, 150).padTop(-30).padBottom(40).row();
        table.add(exitButton).size(400, 150);
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
    }

    public void pause() {
    }

    public void resume() {
    }

    public void hide() {
    }
}
