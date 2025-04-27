package com.krolewskie_potyczki.model;

public abstract class Unit extends Entity {
    double x, y, speed, damage;
    Lifebar lifebar;
    boolean isPlayersEntity;
    Entity currentTarget;

    Unit(boolean isPlayersEntity, float x, float y) {
        super(isPlayersEntity, x, y);
    }

    @Override
    public void move(double delta) {

    }

}
