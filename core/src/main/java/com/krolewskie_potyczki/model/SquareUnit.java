package com.krolewskie_potyczki.model;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class SquareUnit extends MovingUnit {
    public SquareUnit(boolean isPlayersEntity, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.SQUARE), isPlayersEntity, pos);
    }
}
