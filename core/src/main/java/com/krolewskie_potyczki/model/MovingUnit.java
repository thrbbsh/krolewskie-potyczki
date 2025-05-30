package com.krolewskie_potyczki.model;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;

public class MovingUnit extends Unit {
    MovingUnit(EntityConfig config, boolean isPlayersEntity, Vector2 pos) {
        super(config, isPlayersEntity, pos);
    }
}
