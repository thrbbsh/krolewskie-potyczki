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
        if (type == EntityType.TOWER) entity = new Tower(isPlayersEntity, x, y);
        else if (type == EntityType.TRIANGLE) entity = new TriangleUnit(isPlayersEntity, x, y);
        else if (type == EntityType.SQUARE) entity = new SquareUnit(isPlayersEntity, x, y);
        arena.addEntity(entity);
    }

    public void update(float delta) {
        updatePlayerElixir(delta);
        List<Entity> toRemove = new ArrayList<>();
        for (Entity e: arena.getActiveEntities())
            if (e.isDead()) toRemove.add(e);
        for (Entity e: toRemove)
            arena.removeEntity(e);
        for (Entity e: arena.getActiveEntities())
            e.update(delta, arena.getActiveEntities());
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
