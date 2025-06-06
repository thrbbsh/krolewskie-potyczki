package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.factory.EntityFactory;

import java.util.ArrayList;
import java.util.List;

public class ArenaController {
    private final Arena arena;
    private final EntityFactory entityFactory;

    public ArenaController() {
        arena = new Arena();
        entityFactory = new EntityFactory();
    }

    public void spawnEntity(EntityType type, boolean isPlayersEntity, Vector2 pos) {
        entityFactory.spawnEntity(type, isPlayersEntity, pos.cpy(), arena::addEntity);
    }

    public void update(float delta) {
        arena.updateTimer(-delta);
        arena.updatePlayerElixir(delta);
        List<Entity> toRemove = new ArrayList<>();
        List<Entity> curActiveEntities = new ArrayList<>(arena.getActiveEntities());
        for (Entity e: curActiveEntities)
            if (e.isDead()) {
                toRemove.add(e);
            }

        toRemove.forEach(arena::removeEntity);

        for (Entity e : new ArrayList<>(arena.getActiveEntities()))
            e.update(delta, arena.getActiveEntities());
    }

    public Arena getArena() {
        return arena;
    }
}
