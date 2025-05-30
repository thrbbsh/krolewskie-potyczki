package com.krolewskie_potyczki.model;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class MovingUnit extends Unit {
    MovingUnit(EntityConfig config, boolean isPlayersEntity, Vector2 pos) {
        super(config, isPlayersEntity, pos);
    }

    @Override
    public boolean canBeAttackedBy(EntityType type) {
        return !GameConfig.getInstance().getEntityConfig(type).ignoresMovingUnits;
    }
}
