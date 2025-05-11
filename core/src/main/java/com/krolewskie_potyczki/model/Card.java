package com.krolewskie_potyczki.model;

import com.badlogic.gdx.graphics.Texture;

public class Card {
    private EntityType entityType;
    private int elixirCost;
    public Card(EntityType type) {
        this.entityType = type;
        if (type == EntityType.SQUARE) elixirCost = 3;
        else if (type == EntityType.TRIANGLE) elixirCost = 5;
    }
    boolean canBeSpawned(float currentElixir) {
        return elixirCost <= currentElixir;
    }
    public EntityType getEntityType() {
        return entityType;
    }
    public int getElixirCost() {
        return elixirCost;
    }
}
