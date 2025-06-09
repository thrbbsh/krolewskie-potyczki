package com.krolewskie_potyczki.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class CardView implements Disposable {
    public static final float CARD_VIEW_START_X = GameConfig.getInstance().getCardViewConstants().cardViewStartX;
    public static final float CARD_VIEW_ADD_X = GameConfig.getInstance().getCardViewConstants().cardViewAddX;
    public static final float CARD_VIEW_Y = GameConfig.getInstance().getCardViewConstants().cardViewY;

    private final ImageButton imageButton;
    private final Texture elixirDropTexture;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Vector2 pos;
    private final int elixirCost;

    public CardView(EntityType entityType, int elixirCost, int cardIdx, CardClickListener listener) {
        this.elixirCost = elixirCost;

        font = new BitmapFont();
        font.getData().setScale(2.5f);

        pos = new Vector2(CARD_VIEW_START_X + cardIdx * CARD_VIEW_ADD_X, CARD_VIEW_Y);

        elixirDropTexture = new Texture("images/cards/elixirDrop.png");
        batch = new SpriteBatch();
        Texture texture;
        if (entityType == null) texture = new Texture("images/cards/defaultCard.png");
            else texture = new Texture("images/cards/" + entityType.toString().toLowerCase() + "Card.png");

        imageButton = new ImageButton(new TextureRegionDrawable(texture));
        imageButton.setPosition(pos.x, pos.y);
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                listener.onCardClicked(cardIdx);
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
        batch.draw(elixirDropTexture, pos.x + (imageButton.getWidth() - elixirDropTexture.getWidth() * 1.2f) / 2, pos.y - 10, elixirDropTexture.getWidth() * 1.2f, elixirDropTexture.getHeight() * 1.2f);
        font.setColor(Color.WHITE);
        font.draw(batch, String.valueOf(elixirCost), pos.x + (imageButton.getWidth() - elixirDropTexture.getWidth() * 1.2f) / 2 + 7, pos.y + 25);
        batch.end();
    }
    @Override
    public void dispose() {
        elixirDropTexture.dispose();
        batch.dispose();
        font.dispose();
    }
}
