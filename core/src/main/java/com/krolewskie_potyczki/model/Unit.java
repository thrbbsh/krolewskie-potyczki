package com.krolewskie_potyczki.model;

public abstract class Unit extends Entity {
    float elixirCost;
    Unit(EntityType type, boolean isPlayersEntity, float x, float y) {
        super(type, isPlayersEntity, x, y);
    }
}
