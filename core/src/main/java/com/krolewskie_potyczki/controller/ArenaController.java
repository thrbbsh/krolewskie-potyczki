package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.*;
import com.krolewskie_potyczki.model.config.EntityType;

import java.util.ArrayList;
import java.util.List;

public class ArenaController {
    private final Arena arena;

    public ArenaController(Arena arena) {
        this.arena = arena;
    }

    public void spawnEntity(EntityType type, boolean isPlayersEntity, Vector2 pos) {
        EntityFactory entityFactory = new EntityFactory();
        if (type == EntityType.SKELETON_ARMY) {
            int count = 15, squareSize = 4;
            for (int i = 0; i < squareSize; i++) {
                for (int j = 0; j < squareSize; j++) {
                    count--;
                    if (count < 0)
                        continue;
                    Vector2 currentPos = pos.cpy().add((i - squareSize / 2f) * 50, (j - squareSize / 2f) * 50);
                    arena.addEntity(entityFactory.spawnEntity(EntityType.SKELETON, isPlayersEntity, currentPos));
                }
            }
            return;
        }

        Entity entity = entityFactory.spawnEntity(type, isPlayersEntity, pos.cpy());
        arena.addEntity(entity);
    }

    public void update(float delta) {
        updatePlayerElixir(delta);
        List<Entity> toRemove = new ArrayList<>();
        List<Entity> curActiveEntities = new ArrayList<>(arena.getActiveEntities());
        for (Entity e: curActiveEntities)
            if (e.isDead()) {
                if (e instanceof Spawner) {
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
            if (e instanceof Spawner && ((Spawner) e).isReadyToSpawn()) {
                spawnEntity(EntityType.SKELETON, e.getIsPlayersEntity(), e.getPos());
                ((Spawner) e).setReadyToSpawn(false);
            }
    }

    private void updatePlayerElixir(float delta) {
        float playerElixir = arena.getPlayerElixir();
        float newPlayerElixir = Math.min(playerElixir + delta * arena.getElixirSpeed(), arena.getMaxElixir());
        arena.setPlayerElixir(newPlayerElixir);
    }

    public void spendElixir(int elixirCost) {
        arena.spendElixir(elixirCost);
    }

}
