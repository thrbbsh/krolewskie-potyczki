package com.krolewskie_potyczki.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.krolewskie_potyczki.model.Card;
import com.krolewskie_potyczki.model.EntityType;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class CardView implements Disposable {
    private final Card card;
    private final ImageButton imageButton;

    public CardView(EntityType type, float x, float y, CardClickListener listener) {
        this.card = new Card(type);
        Texture texture;
        if (card.getEntityType() == null) texture = new Texture("images/cards/defaultCard.png");
            else texture = new Texture("images/cards/" + card.getEntityType().toString().toLowerCase() + "Card.png");
        imageButton = new ImageButton(new TextureRegionDrawable(texture));
        imageButton.setPosition(x, y);
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                listener.onCardClicked(card);
            }
        });
    }
    public void addToStage(Stage stage) {
        stage.addActor(imageButton);
    }
    public void render(float ignoredDelta) {}
    @Override
    public void dispose() {}
}
