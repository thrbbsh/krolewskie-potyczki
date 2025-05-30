package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class SideTower extends Tower {
    public SideTower(boolean isPlayersEntity, float x, float y) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.SIDE_TOWER), isPlayersEntity, x, y);
    }
}
