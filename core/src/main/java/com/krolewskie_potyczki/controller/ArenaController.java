package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.building.Spawner;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.factory.CompositeEntityFactory;
import com.krolewskie_potyczki.model.factory.EntityFactory;

import java.util.ArrayList;
import java.util.List;

public class ArenaController {
    private final Arena arena;
    private final EntityFactory entityFactory;
    private final CompositeEntityFactory compositeEntityFactory;

    public ArenaController() {
        arena = new Arena();
        entityFactory = new EntityFactory();
        compositeEntityFactory = new CompositeEntityFactory();
    }

    public void spawnEntity(EntityType type, boolean isPlayersEntity, Vector2 pos) {
        //TODO: FIX IT SOMEHOW.
        if (type == EntityType.SKELETON_ARMY) arena.addEntities(compositeEntityFactory.spawnEntity(type, isPlayersEntity, pos.cpy()));
            else arena.addEntity(entityFactory.spawnEntity(type, isPlayersEntity, pos.cpy()));
    }

    public void update(float delta) {
        arena.updateTimer(-delta);
        arena.updatePlayerElixir(delta);
        List<Entity> toRemove = new ArrayList<>();
        List<Entity> curActiveEntities = new ArrayList<>(arena.getActiveEntities());
        for (Entity e: curActiveEntities)
            if (e.isDead()) {
                //TODO: Fix it later.
                if (e.getConfig().type == EntityType.TOMBSTONE) {
                    spawnEntity(EntityType.SKELETON, e.getIsPlayersEntity(), e.getPos().add(0, 20));
                    spawnEntity(EntityType.SKELETON, e.getIsPlayersEntity(), e.getPos().add(-20, 40));
                    spawnEntity(EntityType.SKELETON, e.getIsPlayersEntity(), e.getPos().add(20, 40));
                }
                toRemove.add(e);
            }

        for (Entity e: toRemove)
            arena.removeEntity(e);
        for (Entity e: arena.getActiveEntities())
            e.update(delta, arena.getActiveEntities());
        curActiveEntities = new ArrayList<>(arena.getActiveEntities());
        for (Entity e: curActiveEntities)
            if (e.isReadyToSpawn()) {
                spawnEntity(EntityType.SKELETON, e.getIsPlayersEntity(), e.getPos());
                ((Spawner) e).setReadyToSpawn(false);
            }
    }

    public Arena getArena() {
        return arena;
    }
}
