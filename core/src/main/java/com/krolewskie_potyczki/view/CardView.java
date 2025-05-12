package com.krolewskie_potyczki.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private final Texture elixirDropTexture;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final float x, y;

    public CardView(EntityType type, float x, float y, CardClickListener listener) {
        this.x = x;
        this.y = y;
        font = new BitmapFont();
        font.getData().setScale(2.5f);
        this.card = new Card(type);
        elixirDropTexture = new Texture("images/cards/elixirDrop.png");
        batch = new SpriteBatch();
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

    public void setSelected(boolean selected) {
        if (selected)
            imageButton.getImage().setColor(Color.GRAY);
        else
            imageButton.getImage().setColor(Color.WHITE);
    }

    public void addToStage(Stage stage) {
        stage.addActor(imageButton);
        batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
    }
    public void render(float ignoredDelta) {
        batch.begin();
        batch.draw(elixirDropTexture, x + (imageButton.getWidth() - elixirDropTexture.getWidth() * 1.2f) / 2, y - 10, elixirDropTexture.getWidth() * 1.2f, elixirDropTexture.getHeight() * 1.2f);
        font.setColor(Color.WHITE);
        font.draw(batch, String.valueOf(card.getElixirCost()), x + (imageButton.getWidth() - elixirDropTexture.getWidth() * 1.2f) / 2 + 7, y + 25);
        batch.end();
    }
    @Override
    public void dispose() {}

    public Card getCard() {
        return card;
    }
}
