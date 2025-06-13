package com.krolewskie_potyczki.model.result;

public record MatchResult(
    Winner winner,
    int playerCrowns,
    int botCrowns,
    float playerMinTowerHP,
    float botMinTowerHP
) {}
