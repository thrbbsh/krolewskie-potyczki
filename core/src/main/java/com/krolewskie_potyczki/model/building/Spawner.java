package com.krolewskie_potyczki.model.building;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.entity.Entity;

import java.util.List;
import java.util.function.Consumer;

public abstract class Spawner extends Building {
    private float curInterval;
    private final float spawnInterval;
    private final Consumer<Entity> doSpawn;

    Spawner(EntityConfig config, TeamType teamType, Vector2 pos, Consumer<Entity> doSpawn) {
        super(config, teamType, pos);
        curInterval = 0;
        spawnInterval = config.spawnInterval;
        this.doSpawn = doSpawn;
    }
    @Override
    public void update(float delta, List<Entity> activeEntities) {
        super.update(delta, activeEntities);
        receiveDamage(delta * config.totalHP / 30f);

        curInterval += delta;
        if (curInterval > spawnInterval) {
            curInterval -= spawnInterval;
            doSpawn.accept(newEntity(getViewPos().cpy()));
        }
    }

    @Override
    public void onDeath() {
        doSpawn.accept(newEntity(getViewPos().cpy().add(0, 20)));
        doSpawn.accept(newEntity(getViewPos().cpy().add(20, 40)));
        doSpawn.accept(newEntity(getViewPos().cpy().add(-20, 40)));
    }

    protected abstract Entity newEntity(Vector2 pos);
}
