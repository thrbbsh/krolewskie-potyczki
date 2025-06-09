package com.krolewskie_potyczki.model.unit;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class SkeletonUnit extends Unit {
    public SkeletonUnit(TeamType teamType, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.SKELETON), teamType, pos);
    }
}
