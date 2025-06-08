package com.krolewskie_potyczki.model.building;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.unit.SkeletonUnit;

import java.util.function.Consumer;

public class Tombstone extends Spawner {
    public Tombstone(TeamType teamType, Vector2 pos, Consumer<Entity> doSpawn) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.TOMBSTONE), teamType, pos, doSpawn);
    }

    @Override
    protected Entity newEntity(Vector2 pos) {
        return new SkeletonUnit(getTeamType(), pos);
    }
}

