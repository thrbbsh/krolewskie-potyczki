package com.krolewskie_potyczki.model.building;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.entity.Entity;

public abstract class Building extends Entity {
    Building(EntityConfig config, TeamType teamType, Vector2 pos) {
        super(config, teamType, pos);
    }
}
