package com.krolewskie_potyczki.model;

public class SquareUnit extends MovingUnit {
    public SquareUnit(boolean isPlayersEntity, float x, float y) {
        super(EntityType.Square, isPlayersEntity, x, y);
    }
}
