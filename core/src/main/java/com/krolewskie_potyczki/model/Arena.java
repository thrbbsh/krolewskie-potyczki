package com.krolewskie_potyczki.model;

import java.util.ArrayList;
import java.util.List;

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
        return 0.3F;
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
        activeEntities.add(createEntity(EntityType.MainTower, true, 380, 655));
        activeEntities.add(createEntity(EntityType.SideTower, true, 470, 405));
        activeEntities.add(createEntity(EntityType.SideTower, true, 470, 905));
        activeEntities.add(createEntity(EntityType.MainTower, false, 1815, 655));
        activeEntities.add(createEntity(EntityType.SideTower, false, 1725, 405));
        activeEntities.add(createEntity(EntityType.SideTower, false, 1725, 905));
        playerElixir = 5;
    }

    public Entity createEntity(EntityType entityType, boolean isPlayersEntity, float x, float y) {
        switch (entityType) {
            case MainTower:
                return new MainTower(isPlayersEntity, x, y);
            case SideTower:
                return new SideTower(isPlayersEntity, x, y);
            case Square:
                return new SquareUnit(isPlayersEntity, x, y);
            case Triangle:
                return new TriangleUnit(isPlayersEntity, x, y);
            default:
                throw new IllegalArgumentException("Wrong entity type: " + entityType);
        }
    }

    public int PlayerCrownsCount() {
        int crowns = 3;
        for (Entity e : activeEntities) {
            if ((e instanceof MainTower || e instanceof SideTower) && !e.getIsPlayersEntity()) crowns--;
        }
        return crowns;
    }

    public int EnemyCrownsCount() {
        int crowns = 3;
        for (Entity e : activeEntities) {
            if ((e instanceof MainTower || e instanceof SideTower) && e.getIsPlayersEntity()) crowns--;
        }
        return crowns;
    }

    public float getMinPlayerTowerHP() {
        float min = -1;
        for (Entity e : activeEntities) {
            if (!(e instanceof MainTower || e instanceof SideTower) || !e.getIsPlayersEntity()) continue;
            if (min == -1) min = e.getHP();
                else min = (int) Math.min(min, e.getHP());
        }
        if (min == -1) return 0;
            else return min;
    }

    public float getMinEnemyTowerHP() {
        float min = -1;
        for (Entity e : activeEntities) {
            if (!(e instanceof MainTower || e instanceof SideTower) || e.getIsPlayersEntity()) continue;
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
