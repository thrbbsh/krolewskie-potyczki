package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.krolewskie_potyczki.Main;

public class MenuView implements Disposable {
    private Stage stage;
    private Skin skin;
    private Label titleLabel;

    public MenuView(Main game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skins/craftacular-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
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

        table.top();
        table.add(titleLabel).padTop(150);
        table.row();

        table.add(startButton).size(400, 150).padTop(100).padBottom(40).row();
        table.add(exitButton).size(400, 150);
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
