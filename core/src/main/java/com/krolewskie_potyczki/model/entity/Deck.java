package com.krolewskie_potyczki.model.entity;

import com.krolewskie_potyczki.model.config.EntityType;

public class Deck {
    public static final int DECK_SIZE = 4;

    EntityType[] deckCards;
    Card[] cards;

    int selectedCardIdx;

    public Deck(EntityType[] deckCards) {
        this.deckCards = deckCards;

        cards = new Card[DECK_SIZE];

        for (int i = 0; i < deckCards.length; i++) {
            cards[i] = new Card(deckCards[i]);
        }

        selectedCardIdx = -1;
    }

    public EntityType getDeckCardEntityType(int i) {
        return cards[i].getEntityType();
    }

    public int getDeckCardElixirCost(int i) {
        return cards[i].getElixirCost();
    }

    public void setSelectedCardIdx(int i) {
        selectedCardIdx = i;
    }

    public int getSelectedCardIdx() {
        return selectedCardIdx;
    }

    public boolean someCardIsSelected() {
        return selectedCardIdx != -1;
    }

    public int getSelectedCardElixirCost() {
        return cards[selectedCardIdx].getElixirCost();
    }

    public EntityType getSelectedCardEntityType() {
        return cards[selectedCardIdx].getEntityType();
    }
}
