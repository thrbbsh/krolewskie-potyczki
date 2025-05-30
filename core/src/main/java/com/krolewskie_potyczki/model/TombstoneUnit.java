package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class TombstoneUnit extends Spawner {
    public TombstoneUnit(boolean isPlayersEntity, float x, float y) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.TOMBSTONE), isPlayersEntity, x, y);
    }
}

