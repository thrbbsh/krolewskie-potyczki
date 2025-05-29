package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.GameConfig;

public class TriangleUnit extends MovingUnit {
    public TriangleUnit(boolean isPlayersEntity, float x, float y) {
        super(GameConfig.getInstance().getEntityConfig("Triangle"), isPlayersEntity, x, y);
    }
}
