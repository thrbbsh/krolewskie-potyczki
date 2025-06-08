package com.krolewskie_potyczki.model.unit;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class SkeletonArmy extends CompositeUnit {
    private static final int SKELETON_COUNT = 15;

    public SkeletonArmy(TeamType teamType, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.SKELETON_ARMY), teamType, pos);
        for (int i = 0; i < SKELETON_COUNT; i++) {
            Vector2 childPos = calculateOffsetPosition(pos, i, SKELETON_COUNT);
            addChild(new SkeletonUnit(teamType, childPos));
        }
    }
}
