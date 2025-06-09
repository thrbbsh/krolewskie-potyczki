package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.view.ArenaView;
import com.krolewskie_potyczki.view.CardView;
import com.krolewskie_potyczki.model.entity.Deck;

public class DeckController {
    public static final float LEFT_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().leftBorder;
    public static final float RIGHT_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().rightBorder;
    public static final float UP_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().upBorder;
    public static final float DOWN_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().downBorder;

    private final Arena arena;
    private final Deck deck;
    private final CardView[] cardViews;
    private final ArenaView arenaView;
    private final ArenaController arenaController;

    public DeckController(Arena arena, ArenaView arenaView, ArenaController arenaController) {
        this.arena = arena;
        this.arenaView = arenaView;
        this.arenaController = arenaController;

        EntityType[] deckCards = new EntityType[]{
            EntityType.ARCHER_ARMY,
            EntityType.SQUARE,
            EntityType.TRIANGLE,
            EntityType.VALKYRIE,
            EntityType.TOMBSTONE,
            EntityType.INFERNO,
            EntityType.SKELETON_ARMY
        };
        deck = new Deck(deckCards);

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

    public void onMapTouched(Vector2 pos) {
        if (!deck.someCardIsSelected() || arena.getPlayerElixir() < deck.getSelectedCardElixirCost() ||
            !(LEFT_BORDER <= pos.x && pos.x <= RIGHT_BORDER && DOWN_BORDER <= pos.y && pos.y <= UP_BORDER))
            return;
        arenaController.spawnEntity(deck.getSelectedCardEntityType(), TeamType.PLAYER, pos);
        arena.spendElixir(deck.getSelectedCardElixirCost());
        int idx = deck.getSelectedCardIdx();
        cardViews[idx].setSelected(false);
        deck.selectedCardWasChosen();
        cardViews[idx].dispose();
        cardViews[idx] = arenaView.createCardView(deck.getDeckCardEntityType(idx), deck.getDeckCardElixirCost(idx), idx, this::onCardClicked);
        deck.setSelectedCardIdx(-1);
        arenaView.hideGhostEntity();
    }

    public void update(float delta) {
        for (int i = 0; i < Deck.DECK_SIZE; i++) {
            cardViews[i].render(delta);
        }
    }
}
