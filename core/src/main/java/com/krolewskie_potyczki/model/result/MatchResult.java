package com.krolewskie_potyczki.model.result;

public record MatchResult(
    Winner winner,
    int playerCrowns,
    int enemyCrowns,
    float playerMinTowerHP,
    float enemyMinTowerHP
) {}
