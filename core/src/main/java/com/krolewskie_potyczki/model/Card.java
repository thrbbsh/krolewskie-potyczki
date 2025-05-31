package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.EntityType;

public class Card {
    private final EntityType type;
    private int elixirCost;
    public Card(EntityType entityType) {
        this.type = entityType;
        // TODO: fix it!!
        if (type == EntityType.SQUARE) elixirCost = 3;
        else if (type == EntityType.TRIANGLE) elixirCost = 5;
        else if (type == EntityType.TOMBSTONE) elixirCost = 4;
        else if (type == EntityType.SKELETON_ARMY) elixirCost = 7;
    }
    public EntityType getEntityType() {
        return type;
    }
    public int getElixirCost() {
        return elixirCost;
    }
}
