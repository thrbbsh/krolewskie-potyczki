package com.krolewskie_potyczki.model;

public class SquareUnit extends Unit {
    SquareUnit(boolean isPlayersEntity, float x, float y) {
        super(isPlayersEntity, x, y);
        attackRadius = 1.0F;
        speed = 1000.0F;
    }
}
