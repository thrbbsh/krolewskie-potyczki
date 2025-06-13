package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.view.ArenaView;
import com.krolewskie_potyczki.view.CardView;
import com.krolewskie_potyczki.model.entity.Deck;

import java.util.List;

public class DeckController {
    public static final float UP_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().upBorder;
    public static final float DOWN_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().downBorder;
    public static final float LEFT_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().leftBorder;
    public static final float RIVER_X_START = GameConfig.getInstance().getZonePointsConstantsConfig().riverXStart;

    public static final List<EntityType> SPAWN_LIST = GameConfig.getInstance().getDeckConstants().spawnList;

    private final Arena arena;
    private final Deck deck;
    private final CardView[] cardViews;
    private final ArenaView arenaView;
    private final ArenaController arenaController;

    public DeckController(Arena arena, ArenaView arenaView, ArenaController arenaController) {
        this.arena = arena;
        this.arenaView = arenaView;
        this.arenaController = arenaController;

        deck = new Deck(SPAWN_LIST);

        cardViews = new CardView[Deck.DECK_SIZE];

        for (int i = 0; i < Deck.DECK_SIZE; i++) {
            cardViews[i] = arenaView.createCardView(deck.getDeckCardEntityType(i), deck.getDeckCardElixirCost(i), i, this::onCardClicked);
        }
    }

    public void onCardClicked(int cardIdx) {
        if (arena.getPlayerElixir() < deck.getDeckCardElixirCost(cardIdx)) {
            return;
        }

        if (deck.getSelectedCardIdx() == cardIdx) {
            cardViews[cardIdx].setSelected(false);
            deck.setSelectedCardIdx(-1);
            arenaController.hideGhostEntity();
        } else {
            if (deck.someCardIsSelected()) {
                cardViews[deck.getSelectedCardIdx()].setSelected(false);
                arenaController.hideGhostEntity();
            }
            deck.setSelectedCardIdx(cardIdx);
            cardViews[deck.getSelectedCardIdx()].setSelected(true);
            arenaController.showGhostEntity(deck.getSelectedCardEntityType());
        }
    }

    public void onMapTouched(Vector2 pos) {
        if (!deck.someCardIsSelected() || arena.getPlayerElixir() < deck.getSelectedCardElixirCost() ||
            !(LEFT_BORDER <= pos.x && pos.x <= RIVER_X_START && DOWN_BORDER <= pos.y && pos.y <= UP_BORDER))
            return;
        int elixirCost = deck.getSelectedCardElixirCost();
        int idx = deck.getSelectedCardIdx();

        arenaController.spawnEntity(deck.getSelectedCardEntityType(), TeamType.PLAYER, pos);
        deck.selectedCardWasChosen();
        arena.spendElixir(elixirCost);
        cardViews[idx].setSelected(false);
        cardViews[idx].dispose();
        cardViews[idx] = arenaView.createCardView(deck.getDeckCardEntityType(idx), deck.getDeckCardElixirCost(idx), idx, this::onCardClicked);
        arenaController.hideGhostEntity();
    }

    public void update(float delta) {
        for (int i = 0; i < Deck.DECK_SIZE; i++) {
            cardViews[i].render(delta);
        }
    }
}
