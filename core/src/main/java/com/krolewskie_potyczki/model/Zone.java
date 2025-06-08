package com.krolewskie_potyczki.model;

import com.badlogic.gdx.math.Vector2;

public enum Zone {
    BLUE_BASE,
    UPPER_BRIDGE,
    LOWER_BRIDGE,
    RED_BASE;

    public static Zone getZone(Vector2 pos) {
        if (pos.x <= 1032) return Zone.BLUE_BASE;
        if (pos.x >= 1163) return Zone.RED_BASE;
        if (pos.y >= 500) return Zone.UPPER_BRIDGE;
        return Zone.LOWER_BRIDGE;
    }
}
