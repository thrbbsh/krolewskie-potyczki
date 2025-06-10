package com.krolewskie_potyczki.model;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.GameConfig;

public enum Zone {
    BLUE_BASE,
    UPPER_BRIDGE,
    LOWER_BRIDGE,
    RED_BASE;

    public static final float RIVER_X_START = GameConfig.getInstance().getZonePointsConstantsConfig().riverXStart;
    public static final float RIVER_X_END = GameConfig.getInstance().getZonePointsConstantsConfig().riverXEnd;
    public static final float RIVER_Y_MID_PART_START = GameConfig.getInstance().getZonePointsConstantsConfig().riverYMidPartStart;

    public static Zone getZone(Vector2 pos) {
        if (pos.x <= RIVER_X_START) return Zone.BLUE_BASE;
        if (pos.x >= RIVER_X_END) return Zone.RED_BASE;
        if (pos.y >= RIVER_Y_MID_PART_START + 50) return Zone.UPPER_BRIDGE;
        return Zone.LOWER_BRIDGE;
    }
}
