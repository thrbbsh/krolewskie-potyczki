package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.building.Spawner;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.endcondition.DefaultGameEndCondition;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.factory.EntityFactory;
import com.krolewskie_potyczki.model.result.MatchResult;

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
        arena.updateTimer(-delta);
        arena.updatePlayerElixir(delta);
        List<Entity> toRemove = new ArrayList<>();
        List<Entity> curActiveEntities = new ArrayList<>(arena.getActiveEntities());
        for (Entity e: curActiveEntities)
            if (e.isDead()) {
                if (e.getConfig().type == EntityType.SKELETON_ARMY) {
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

    public float getPlayerElixir() {
        return arena.getPlayerElixir();
    }

    public float getMaxElixir() {
        return arena.getMaxElixir();
    }

    public float getTimeLeft() {
        return arena.getTimeLeft();
    }

    public float getElixirSpeed() {
        return arena.getElixirSpeed();
    }

    public void spendElixir(int elixirCost) {
        arena.spendElixir(elixirCost);
    }

    public List<Entity> getActiveEntities() {
        return arena.getActiveEntities();
    }

    public boolean isGameOver(DefaultGameEndCondition endCondition) {
        return endCondition.isGameOver(arena);
    }

    public MatchResult calculateResult(DefaultGameEndCondition endCondition) {
        return endCondition.calculateResult(arena);
    }
}
