package com.krolewskie_potyczki.controller;

import com.krolewskie_potyczki.model.*;

import java.util.ArrayList;
import java.util.List;

public class ArenaController {
    private final Arena arena;

    public ArenaController(Arena arena) {
        this.arena = arena;
    }

    public void spawnEntity(String type, boolean isPlayersEntity, float x, float y) {
        EntityFactory entityFactory = new EntityFactory();
        if (type.equals("SkeletonArmy")) {
            int count = 15, squareSize = 4;
            for (int i = 0; i < squareSize; i++) {
                for (int j = 0; j < squareSize; j++) {
                    count--;
                    if (count < 0)
                        continue;
                    arena.addEntity(entityFactory.spawnEntity("Skeleton", isPlayersEntity, x + (i - squareSize / 2f) * 50, y + (j - squareSize / 2f) * 50));
                }
            }
            return;
        }

        Entity entity = entityFactory.spawnEntity(type, isPlayersEntity, x, y);
        arena.addEntity(entity);
    }

    public void update(float delta) {
        updatePlayerElixir(delta);
        List<Entity> toRemove = new ArrayList<>();
        List<Entity> curActiveEntities = new ArrayList<>(arena.getActiveEntities());
        for (Entity e: curActiveEntities)
            if (e.isDead()) {
                if (e instanceof Spawner) {
                    spawnEntity("Skeleton", e.getIsPlayersEntity(), e.getX(), e.getY() + 40);
                    spawnEntity("Skeleton", e.getIsPlayersEntity(), e.getX() - 20, e.getY() + 40);
                    spawnEntity("Skeleton", e.getIsPlayersEntity(), e.getX() + 20, e.getY() + 40);
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
                spawnEntity("Skeleton", e.getIsPlayersEntity(), e.getX(), e.getY());
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
