package com.krolewskie_potyczki.model;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    List<Entity> activeEntities;
    private float playerElixir = 0;
    private float timeLeft = 180;
    private final Tower playerTower;
    private final Tower enemyTower;

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
        playerTower = (Tower) createEntity(EntityType.TOWER, true, 335, 595);
        enemyTower = (Tower) createEntity(EntityType.TOWER, false, 1765, 595);
        activeEntities.add(playerTower);
        activeEntities.add(enemyTower);
        playerElixir = 5;
    }

    public Entity createEntity(EntityType entityType, boolean isPlayersEntity, float x, float y) {
        switch (entityType) {
            case TOWER:
                return new Tower(isPlayersEntity, x, y);
            case SQUARE:
                return new SquareUnit(isPlayersEntity, x, y);
            case TRIANGLE:
                return new TriangleUnit(isPlayersEntity, x, y);
            default:
                throw new IllegalArgumentException("Wrong entity type: " + entityType);
        }
    }

    public boolean isPlayerTowerDestroyed() {
        return playerTower.isDead();
    }

    public boolean isEnemyTowerDestroyed() {
        return enemyTower.isDead();
    }

    public float getPlayerTowerHP() {
        return playerTower.getCurrentHP();
    }

    public float getEnemyTowerHP() {
        return enemyTower.getCurrentHP();
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
