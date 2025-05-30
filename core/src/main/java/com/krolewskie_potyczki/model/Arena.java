package com.krolewskie_potyczki.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.EntityFactory;
import com.krolewskie_potyczki.model.config.EntityType;

public class Arena {
    List<Entity> activeEntities;
    private float playerElixir = 0;
    private float timeLeft = 180;

    public float getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(float timeLeft) {
        this.timeLeft = timeLeft;
    }

    public float getElixirSpeed() {
        return 1 / 2.8f;
    }

    public String getFormattedTimeLeft() {
        return String.format("%01d:%02d", (int) Math.floor(timeLeft / 60), (int) Math.floor(timeLeft % 60));
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

    public int CrownsCount(boolean isPlayer) {
        if (MainTowerDestroyed(isPlayer)) return 3;
        int crowns = 3;
        for (Entity e : activeEntities) {
            if ((e instanceof MainTower || e instanceof SideTower) && e.getIsPlayersEntity() == !isPlayer) crowns--;
        }
        return crowns;
    }

    public boolean MainTowerDestroyed(boolean isPlayer) {
        for (Entity e : activeEntities) {
            if (e instanceof MainTower && e.getIsPlayersEntity() == isPlayer) return false;
        }
        return true;
    }

    public float getMinTowerHP(boolean isPlayer) {
        float min = -1;
        for (Entity e : activeEntities) {
            if (!(e instanceof MainTower || e instanceof SideTower) || e.getIsPlayersEntity() == !isPlayer) continue;
            if (min == -1) min = e.getHP();
                else min = (int) Math.min(min, e.getHP());
        }
        if (min == -1) return 0;
            else return min;
    }

    public void spendElixir(float elixirCost) {
        playerElixir -= elixirCost;
    }

    public void setPlayerElixir(float playerElixir) {
        this.playerElixir = playerElixir;
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
}
