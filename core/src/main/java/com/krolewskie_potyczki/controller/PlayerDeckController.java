package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.view.ArenaView;
import com.krolewskie_potyczki.view.CardView;
import com.krolewskie_potyczki.model.entity.Deck;

public class PlayerDeckController extends DeckController {
    public static final float LEFT_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().leftBorder;
    public static final float RIVER_X_START = GameConfig.getInstance().getZonePointsConstantsConfig().riverXStart;

    private final CardView[] cardViews;

    public PlayerDeckController(Arena arena, ArenaView arenaView, ArenaController arenaController) {
        super(arena, arenaView, arenaController);
        teamType = TeamType.PLAYER;
        cardViews = new CardView[Deck.DECK_SIZE];

        for (int i = 0; i < Deck.DECK_SIZE; i++) {
            cardViews[i] = arenaView.createCardView(deck.getDeckCardEntityType(i), deck.getDeckCardElixirCost(i), i, this::onCardClicked);
        }
    }

    @Override
    public void onCardClicked(int cardIdx) {
        if (arena.getPlayerElixir() < deck.getDeckCardElixirCost(cardIdx)) {
            return;
        }

        if (deck.getSelectedCardIdx() == cardIdx) {
            cardViews[cardIdx].setSelected(false);
            deck.setSelectedCardIdx(-1);
            arenaView.hideGhostEntity();
        } else {
            if (deck.someCardIsSelected()) {
                cardViews[deck.getSelectedCardIdx()].setSelected(false);
                arenaView.hideGhostEntity();
            }
            deck.setSelectedCardIdx(cardIdx);
            cardViews[deck.getSelectedCardIdx()].setSelected(true);
            arenaView.showGhostEntity(deck.getSelectedCardEntityType());
        }
    }

    @Override
    public void onMapTouched(Vector2 pos) {
        if (!deck.someCardIsSelected() || arena.getPlayerElixir() < deck.getSelectedCardElixirCost() ||
            !(LEFT_BORDER <= pos.x && pos.x <= RIVER_X_START && DOWN_BORDER <= pos.y && pos.y <= UP_BORDER))
            return;
        int elixirCost = deck.getSelectedCardElixirCost();
        int idx = deck.getSelectedCardIdx();

        super.onMapTouched(pos);

        arena.spendElixir(elixirCost);
        cardViews[idx].setSelected(false);
        cardViews[idx].dispose();
        cardViews[idx] = arenaView.createCardView(deck.getDeckCardEntityType(idx), deck.getDeckCardElixirCost(idx), idx, this::onCardClicked);
        arenaView.hideGhostEntity();
    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < Deck.DECK_SIZE; i++) {
            cardViews[i].render(delta);
        }
    }
}
