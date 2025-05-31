package com.krolewskie_potyczki.model.building;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class SideTower extends Tower {
    public SideTower(boolean isPlayersEntity, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.SIDE_TOWER), isPlayersEntity, pos);
    }
}
