package com.krolewskie_potyczki.model;

public class Card {
    private final EntityType entityType;
    private int elixirCost;
    public Card(EntityType type) {
        this.entityType = type;
        if (type == EntityType.SQUARE) elixirCost = 3;
        else if (type == EntityType.TRIANGLE) elixirCost = 5;
    }
    public EntityType getEntityType() {
        return entityType;
    }
    public int getElixirCost() {
        return elixirCost;
    }
}
