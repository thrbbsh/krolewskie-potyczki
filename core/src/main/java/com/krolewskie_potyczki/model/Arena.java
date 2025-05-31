package com.krolewskie_potyczki.model;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.math.Vector2;
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
        EntityFactory factory = new EntityFactory();
        activeEntities.add(factory.spawnEntity(EntityType.MAIN_TOWER, true, new Vector2(380, 655)));
        activeEntities.add(factory.spawnEntity(EntityType.SIDE_TOWER, true, new Vector2(470, 405)));
        activeEntities.add(factory.spawnEntity(EntityType.SIDE_TOWER, true, new Vector2(470, 905)));
        activeEntities.add(factory.spawnEntity(EntityType.MAIN_TOWER, false, new Vector2(1815, 655)));
        activeEntities.add(factory.spawnEntity(EntityType.SIDE_TOWER, false, new Vector2(1725, 405)));
        activeEntities.add(factory.spawnEntity(EntityType.SIDE_TOWER, false, new Vector2(1725, 905)));
        playerElixir = 5;
    }

    public int crownsCount(boolean isPlayer) {
        if (mainTowerDestroyed(!isPlayer))
            return 3;
        int crowns = 3;
        for (Entity e : activeEntities)
            if (e.isTowerForPlayer(!isPlayer))
                crowns--;
        return crowns;
    }

    public boolean mainTowerDestroyed(boolean isPlayer) {
        for (Entity e : activeEntities)
            if (e.config.type == EntityType.MAIN_TOWER && e.getIsPlayersEntity() == isPlayer)
                return false;
        return true;
    }

    public float getMinTowerHP(boolean isPlayer) {
        return activeEntities.stream().filter(e -> e.isTowerForPlayer(isPlayer)).map(Entity::getHP).min(Float::compare).orElse(0f);
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
}
