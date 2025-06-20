package com.krolewskie_potyczki.model.unit;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Entity;

import java.util.List;

public class ValkyrieUnit extends Unit {
    public ValkyrieUnit(TeamType teamType, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.VALKYRIE), teamType, pos);
    }

    @Override
    public void attack(List<Entity> activeEntities) {
        for (Entity entity : activeEntities) {
            if (getTeamType() != entity.getTeamType() && entity.canBeAttackedBy(config.type) && directDistance(entity) <= config.attackRadius) {
                entity.receiveDamage(config.damage);
            }
        }
    }
}
