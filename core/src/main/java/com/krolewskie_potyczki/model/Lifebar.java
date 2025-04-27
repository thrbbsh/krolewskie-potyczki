package com.krolewskie_potyczki.model;

public class Lifebar {
    double current, total;
    public Lifebar(double total) {
        this.current = total;
        this.total = total;
    }

    void update(double change) {
        current = Math.min(Math.max(0.0, current + change), total);
    }

    boolean isDead() {
        return (current >= 0.0);
    }
}
