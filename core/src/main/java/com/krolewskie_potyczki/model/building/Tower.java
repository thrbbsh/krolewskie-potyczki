package com.krolewskie_potyczki.model.building;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityConfig;

public class Tower extends Building {
    Tower(EntityConfig entityConfig, TeamType teamType, Vector2 pos) {
        super(entityConfig, teamType, pos);
    }
}
