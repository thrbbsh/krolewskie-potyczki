package com.krolewskie_potyczki.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.MatchResult;
import com.krolewskie_potyczki.model.Winner;

public class EndGameView implements Disposable {
    private final Stage stage;
    private final Skin skin;

    public EndGameView(GameController controller, MatchResult result) {
        stage = new Stage(new FitViewport(1920, 1080));

        skin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));

        Table endTable = new Table();
        endTable.setFillParent(true);
        Label.LabelStyle ls = new Label.LabelStyle(skin.getFont("font"), Color.WHITE);

        Label endLabel = new Label("", ls);
        endLabel.setFontScale(3f);
        endLabel.setAlignment(Align.center);

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

        String status;
        switch (result.getWinner()) {
            case PLAYER: status = "You Win!"; break;
            case ENEMY: status = "You Lose."; break;
            default: status = "Draw."; break;
        }

        status += "\n" + String.format("%d:%d", result.getPlayerCrowns(), result.getEnemyCrowns());

        String detail = "";

        if (result.getPlayerCrowns() == result.getEnemyCrowns() &&
            result.getWinner() != Winner.DRAW)
            detail = String.format(
                "\nPlayer HP: %.0f\nEnemy HP: %.0f",
                result.getPlayerMinTowerHP(),
                result.getEnemyMinTowerHP()
            );

        endLabel.setText(status + detail);
        stage.addActor(endTable);
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
