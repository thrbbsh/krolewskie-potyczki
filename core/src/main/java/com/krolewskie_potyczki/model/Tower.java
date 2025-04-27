package com.krolewskie_potyczki.model;

public class Tower extends Entity {
    Tower(boolean isPlayersEntity, float x, float y) {
        super(isPlayersEntity, x, y);
        attackRadius = 1.0F;
        speed = 0;
    }
}
