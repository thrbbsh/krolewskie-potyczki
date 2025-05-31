package com.krolewskie_potyczki.model.result;

public class MatchResult {
    private final Winner winner;
    private final int playerCrowns;
    private final int enemyCrowns;
    private final float playerMinTowerHP;
    private final float enemyMinTowerHP;

    public MatchResult(Winner winner, int playerCrowns, int enemyCrowns, float playerMinTowerHP, float enemyMinTowerHP) {
        this.winner = winner;
        this.playerCrowns = playerCrowns;
        this.enemyCrowns = enemyCrowns;
        this.playerMinTowerHP = playerMinTowerHP;
        this.enemyMinTowerHP = enemyMinTowerHP;
    }

    public Winner getWinner() { return winner; }
    public int getPlayerCrowns() { return playerCrowns; }
    public int getEnemyCrowns() { return enemyCrowns; }
    public float getPlayerMinTowerHP() { return playerMinTowerHP; }
    public float getEnemyMinTowerHP() { return enemyMinTowerHP; }
}
