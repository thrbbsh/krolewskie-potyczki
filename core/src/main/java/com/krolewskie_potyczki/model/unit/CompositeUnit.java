package com.krolewskie_potyczki.model.unit;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class CompositeUnit extends Unit {
    public static final int ENTITY_OFFSET_X = GameConfig.getInstance().getCompositeUnitConstantsConfig().entityOffsetX;
    public static final int ENTITY_OFFSET_Y = GameConfig.getInstance().getCompositeUnitConstantsConfig().entityOffsetY;

    private final List<Entity> entities;
    CompositeUnit(EntityConfig config, TeamType teamType, Vector2 pos) {
        super(config, teamType, pos);
        entities = new ArrayList<>();
    }

    void addChild(Unit child) {
        entities.add(child);
    }

    public static Vector2 calculateOffsetPosition(Vector2 pos, int num, int allCount) {
        int rowCount = (int)(allCount / Math.sqrt(allCount)), colCount = ((allCount + rowCount - 1) / rowCount);
        int rowNumber = num / colCount, colNumber = num % colCount;
        return new Vector2(pos.x + (float)(rowNumber - (rowCount - 1) / 2.0) * ENTITY_OFFSET_X, pos.y + (float)(colNumber - (colCount - 1) / 2.0) * ENTITY_OFFSET_Y);
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
