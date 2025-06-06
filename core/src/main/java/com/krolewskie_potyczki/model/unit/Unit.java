package com.krolewskie_potyczki.model.unit;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Entity;

public abstract class Unit extends Entity {
    Unit(EntityConfig config, boolean isPlayersEntity, Vector2 pos) {
        super(config, isPlayersEntity, pos);
    }
    @Override
    public boolean canBeAttackedBy(EntityType type) {
        return !GameConfig.getInstance().getEntityConfig(type).ignoresMovingUnits;
    }
}
