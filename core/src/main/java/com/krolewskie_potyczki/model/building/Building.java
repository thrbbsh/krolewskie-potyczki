package com.krolewskie_potyczki.model.building;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.entity.Entity;

public class Building extends Entity {
    Building(EntityConfig config, boolean isPlayersEntity, Vector2 pos) {
        super(config, isPlayersEntity, pos);
    }
}
