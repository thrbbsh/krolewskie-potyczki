package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.EntityConfig;

public class MovingUnit extends Unit {
    MovingUnit(EntityConfig config, boolean isPlayersEntity, float x, float y) {
        super(config, isPlayersEntity, x, y);
    }
}
