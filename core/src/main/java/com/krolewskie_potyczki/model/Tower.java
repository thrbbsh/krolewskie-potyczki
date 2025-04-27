package com.krolewskie_potyczki.model;

public class Tower extends Entity {
    Tower(boolean isPlayersEntity, float x, float y) {
        super(EntityType.TOWER, isPlayersEntity, x, y);
    }
}
