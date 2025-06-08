package com.krolewskie_potyczki.model.building;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Entity;

import java.util.List;

public class Inferno extends Building {
    public Inferno(TeamType teamType, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.INFERNO), teamType, pos);
    }

    @Override
    public void update(float delta, List<Entity> activeEntities) {
        super.update(delta, activeEntities);
        receiveDamage(delta * config.totalHP / 30f);
    }

    @Override
    protected void attack() {
        if (currentTarget == null)
            return;
        currentTarget.receiveDamage(config.damage * (1 + timeSinceFirstAttack));
    }
}
