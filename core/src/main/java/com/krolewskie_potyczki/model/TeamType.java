package com.krolewskie_potyczki.model;

public enum TeamType {
    PLAYER,
    BOT;

    public static TeamType otherTeamType(TeamType teamType) {
        return teamType == PLAYER ? BOT : PLAYER;
    }
}
