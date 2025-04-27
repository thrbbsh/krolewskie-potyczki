package com.krolewskie_potyczki.model;

public abstract class Unit extends Entity {
    Unit(boolean isPlayersEntity, float x, float y) {
        super(isPlayersEntity, x, y);
    }
}
