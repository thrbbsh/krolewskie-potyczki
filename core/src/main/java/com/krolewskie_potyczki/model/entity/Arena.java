package com.krolewskie_potyczki.model.entity;

import java.util.ArrayList;
import java.util.List;
import com.krolewskie_potyczki.model.config.EntityType;

public class Arena {
    List<Entity> activeEntities;
    private float playerElixir;
    private float timeLeft = 180;

    public float getTimeLeft() {
        return timeLeft;
    }

    public float getElixirSpeed() {
        return 1 / 2.8f;
    }

    public float getPlayerElixir() {
        return playerElixir;
    }

    public float getMaxElixir() {
        return 10;
    }

    public Arena() {
        activeEntities = new ArrayList<>();
        playerElixir = 5;
    }

    public int crownsCount(boolean isPlayer) {
        if (mainTowerDestroyed(!isPlayer))
            return 3;
        return 3 - (int) activeEntities.stream().filter(e -> isTowerForPlayer(e, !isPlayer)).count();
    }

    public boolean mainTowerDestroyed(boolean isPlayer) {
        return activeEntities.stream().noneMatch(e -> e.config.type == EntityType.MAIN_TOWER && e.getIsPlayersEntity() == isPlayer);
    }

    public float getMinTowerHP(boolean isPlayer) {
        return activeEntities.stream().filter(e -> isTowerForPlayer(e, isPlayer)).map(Entity::getHP).min(Float::compare).orElse(0f);
    }

    public void spendElixir(float elixirCost) {
        playerElixir -= elixirCost;
    }

    public void addEntity(Entity entity) {
        activeEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        activeEntities.remove(entity);
    }

    public List<Entity> getActiveEntities() {
        return activeEntities;
    }

    public void updatePlayerElixir(float delta) {
        playerElixir = Math.min(getMaxElixir(), delta * getElixirSpeed() + getPlayerElixir());
    }

    public void updateTimer(float delta) {
        timeLeft += delta;
    }

    private boolean isTowerForPlayer(Entity e, boolean isPlayer) {
        return (e.config.type == EntityType.MAIN_TOWER || e.config.type == EntityType.SIDE_TOWER) && e.getIsPlayersEntity() == isPlayer;
    }
}
