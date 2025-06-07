package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.view.ArenaView;
import com.krolewskie_potyczki.view.CardView;
import com.krolewskie_potyczki.model.entity.Deck;

public class DeckController {
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
            EntityType.SQUARE,
            EntityType.TRIANGLE,
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
            arenaView.setSpawnArea(false);
        } else {
            if (deck.someCardIsSelected()) {
                cardViews[deck.getSelectedCardIdx()].setSelected(false);
            }
            deck.setSelectedCardIdx(cardIdx);
            cardViews[deck.getSelectedCardIdx()].setSelected(true);
            arenaView.setSpawnArea(true);
        }
    }

    public void onMapTouched(Vector2 pos) {
        if (!deck.someCardIsSelected() || arena.getPlayerElixir() < deck.getSelectedCardElixirCost() ||
            !(287 <= pos.x && pos.x <= 1027 && 227 <= pos.y && pos.y <= 1062))
            return;
        arenaController.spawnEntity(deck.getSelectedCardEntityType(), true, pos);
        arena.spendElixir(deck.getSelectedCardElixirCost());
        int idx = deck.getSelectedCardIdx();
        cardViews[idx].setSelected(false);
        deck.selectedCardWasChosen();
        cardViews[idx].dispose();
        cardViews[idx] = arenaView.createCardView(deck.getDeckCardEntityType(idx), deck.getDeckCardElixirCost(idx), idx, this::onCardClicked);
        deck.setSelectedCardIdx(-1);
        arenaView.setSpawnArea(false);
    }

    public void update(float delta) {
        for (int i = 0; i < Deck.DECK_SIZE; i++) {
            cardViews[i].render(delta);
        }
    }
}
