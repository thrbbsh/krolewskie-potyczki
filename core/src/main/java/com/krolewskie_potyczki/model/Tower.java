package com.krolewskie_potyczki.model;

public class Tower extends Entity {
    public Tower(boolean isPlayersEntity, float x, float y) {
        super(EntityType.TOWER, isPlayersEntity, x, y);
    }
}
