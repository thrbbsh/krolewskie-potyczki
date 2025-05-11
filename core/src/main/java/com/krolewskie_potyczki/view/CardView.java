package com.krolewskie_potyczki.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.controller.GameController;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Card;
import com.krolewskie_potyczki.model.Entity;
import com.krolewskie_potyczki.model.EntityType;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class CardView implements Disposable {
    private final Card card;

    public CardView(EntityType type, float x, float y, Stage stage, Arena arena, GameController controller, ArenaView arenaView) {
        this.card = new Card(type);
        Texture texture;
        if (card.getEntityType() == EntityType.TRIANGLE) texture = new Texture("images/cards/triangleCard.png");
        else if (card.getEntityType() == EntityType.SQUARE) texture = new Texture("images/cards/squareCard.png");
        else texture = new Texture("images/cards/defaultCard.png");
        ImageButton imageButton = new ImageButton(new TextureRegionDrawable(texture));
        imageButton.setPosition(x, y);
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (arena.getPlayerElixir() >= card.getElixirCost()) {
                    Entity e = controller.createEntity(type, true, 400, 900);
                    arenaView.addEntity(e, stage);
                    arena.subtractElixir(card.getElixirCost());
                }
            }
        });
        stage.addActor(imageButton);
    }
    public void render(float ignoredDelta) {}
    @Override
    public void dispose() {}
    public void show() {}
    public void resize(int ignoredW, int ignoredH) { }
}
