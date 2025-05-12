package com.krolewskie_potyczki.model;

public class SquareUnit extends Unit {
    public SquareUnit(boolean isPlayersEntity, float x, float y) {
        super(EntityType.SQUARE, isPlayersEntity, x, y);
    }
}
