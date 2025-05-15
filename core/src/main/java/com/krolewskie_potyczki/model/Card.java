package com.krolewskie_potyczki.model;

public class Card {
    private final EntityType entityType;
    private int elixirCost;
    public Card(EntityType type) {
        this.entityType = type;
        if (type == EntityType.Square) elixirCost = 3;
        else if (type == EntityType.Triangle) elixirCost = 5;
        else if (type == EntityType.Tombstone) elixirCost = 7;
    }
    public EntityType getEntityType() {
        return entityType;
    }
    public int getElixirCost() {
        return elixirCost;
    }
}
