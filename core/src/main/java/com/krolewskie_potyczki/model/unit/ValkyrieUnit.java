package com.krolewskie_potyczki.model.unit;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class ValkyrieUnit extends Unit {
    public ValkyrieUnit(boolean isPlayersEntity, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.VALKYRIE), isPlayersEntity, pos);
    }

    public void attack(List<Entity> activeEntities) {
        List<Entity> damagedEntities = new ArrayList<>();
        for (Entity ent: activeEntities)
            if (distance(ent) <= config.attackRadius) damagedEntities.add(ent);
        for (Entity ent: damagedEntities)
            ent.receiveDamage(config.damage);
    }
}
