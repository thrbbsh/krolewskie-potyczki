package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.EntityConfig;

public abstract class Unit extends Entity {
    Unit(EntityConfig config, boolean isPlayersEntity, float x, float y) {
        super(config, isPlayersEntity, x, y);
    }
}
