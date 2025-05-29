package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.EntityConfig;

public class Card {
    private final EntityConfig entityConfig;
    private int elixirCost;
    public Card(EntityConfig entityConfig) {
        this.entityConfig = entityConfig;
        if (entityConfig.type.equals("Square")) elixirCost = 3;
        else if (entityConfig.type.equals("Triangle")) elixirCost = 5;
        else if (entityConfig.type.equals("Tombstone")) elixirCost = 3;
        else if (entityConfig.type.equals("SkeletonArmy")) elixirCost = 7;
    }
    public EntityConfig getEntityType() {
        return entityConfig;
    }
    public int getElixirCost() {
        return elixirCost;
    }
}
