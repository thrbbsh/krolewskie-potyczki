package com.krolewskie_potyczki.controller;

import com.krolewskie_potyczki.model.*;

import java.util.ArrayList;
import java.util.List;

public class ArenaController {
    private final Arena arena;

    public ArenaController(Arena arena) {
        this.arena = arena;
    }

    public void spawnEntity(EntityType type, boolean isPlayersEntity, float x, float y) {
        Entity entity = null;
        if (type == EntityType.MainTower) entity = new MainTower(isPlayersEntity, x, y);
        else if (type == EntityType.SideTower) entity = new SideTower(isPlayersEntity, x, y);
        else if (type == EntityType.Triangle) entity = new TriangleUnit(isPlayersEntity, x, y);
        else if (type == EntityType.Square) entity = new SquareUnit(isPlayersEntity, x, y);
        else if (type == EntityType.Tombstone) entity = new TombstoneUnit(isPlayersEntity, x, y);
        else if (type == EntityType.Skeleton) entity = new SkeletonUnit(isPlayersEntity, x, y);
        arena.addEntity(entity);
    }

    public void update(float delta) {
        updatePlayerElixir(delta);
        List<Entity> toRemove = new ArrayList<>();
        List<Entity> curActiveEntities = new ArrayList<>(arena.getActiveEntities());
        for (Entity e: curActiveEntities)
            if (e.isDead()) {
                if (e instanceof Spawner) {
                    spawnEntity(((Spawner) e).getSpawnType(), e.getIsPlayersEntity(), e.getX(), e.getY() + 40);
                    spawnEntity(((Spawner) e).getSpawnType(), e.getIsPlayersEntity(), e.getX() - 20, e.getY() + 40);
                    spawnEntity(((Spawner) e).getSpawnType(), e.getIsPlayersEntity(), e.getX() + 20, e.getY() + 40);
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
                spawnEntity(((Spawner) e).getSpawnType(), e.getIsPlayersEntity(), e.getX(), e.getY());
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
