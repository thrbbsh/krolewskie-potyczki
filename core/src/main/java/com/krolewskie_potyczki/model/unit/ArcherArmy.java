package com.krolewskie_potyczki.model.unit;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class ArcherArmy extends CompositeUnit {
    private static final int ARCHER_COUNT = 2;

    public ArcherArmy(TeamType teamType, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.ARCHER_ARMY), teamType, pos);
        for (int i = 0; i < ARCHER_COUNT; i++) {
            Vector2 childPos = calculateOffsetPosition(pos, i, ARCHER_COUNT);
            addChild(new ArcherUnit(teamType, childPos));
        }
    }
}
