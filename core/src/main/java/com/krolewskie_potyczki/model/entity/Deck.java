package com.krolewskie_potyczki.model.entity;

import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Deck {
    public static final int DECK_SIZE = GameConfig.getInstance().getArenaConstants().deckSize;

    Card[] deckCards;
    Queue<Card> waitingCards;

    int selectedCardIdx;

    public Deck(List<EntityType> cardTypes) {
        deckCards = new Card[DECK_SIZE];
        waitingCards = new LinkedList<>();

        for (int i = 0; i < cardTypes.size(); i++)
            if (i < DECK_SIZE) deckCards[i] = new Card(cardTypes.get(i));
                else waitingCards.add(new Card(cardTypes.get(i)));
        selectedCardIdx = -1;
    }

    public EntityType getDeckCardEntityType(int i) {
        return deckCards[i].getEntityType();
    }

    public int getDeckCardElixirCost(int i) {
        return deckCards[i].getElixirCost();
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
        return deckCards[selectedCardIdx].getElixirCost();
    }

    public EntityType getSelectedCardEntityType() {
        return deckCards[selectedCardIdx].getEntityType();
    }

    public void selectedCardWasChosen() {
        waitingCards.add(deckCards[selectedCardIdx]);
        deckCards[selectedCardIdx] = waitingCards.poll();
        selectedCardIdx = -1;
    }
}
