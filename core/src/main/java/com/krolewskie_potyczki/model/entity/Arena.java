package com.krolewskie_potyczki.model.entity;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.factory.EntityFactory;

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
        EntityFactory entityFactory = new EntityFactory();
        entityFactory.spawnEntity(EntityType.MAIN_TOWER, true, new Vector2(380, 655), this::addEntity);
        entityFactory.spawnEntity(EntityType.SIDE_TOWER, true, new Vector2(470, 405), this::addEntity);
        entityFactory.spawnEntity(EntityType.SIDE_TOWER, true, new Vector2(470, 905), this::addEntity);
        entityFactory.spawnEntity(EntityType.MAIN_TOWER, false, new Vector2(1815, 655), this::addEntity);
        entityFactory.spawnEntity(EntityType.SIDE_TOWER, false, new Vector2(1725, 405), this::addEntity);
        entityFactory.spawnEntity(EntityType.SIDE_TOWER, false, new Vector2(1725, 905), this::addEntity);
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
