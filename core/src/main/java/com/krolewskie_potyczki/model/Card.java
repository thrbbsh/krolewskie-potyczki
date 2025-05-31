package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class Card {
    private final EntityType type;
    private final int elixirCost;
    public Card(EntityType entityType) {
        type = entityType;
        elixirCost = GameConfig.getInstance().getEntityConfig(entityType).elixirCost;
    }
    public EntityType getEntityType() {
        return type;
    }
    public int getElixirCost() {
        return elixirCost;
    }
}
