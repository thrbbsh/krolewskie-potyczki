package com.krolewskie_potyczki.model.projectile;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.entity.Entity;

public class Projectile extends Entity {
    public Projectile(EntityConfig config, TeamType teamType, Vector2 pos) {
        super(config, teamType, pos);
    }
    @Override
    public boolean canBeAttackedBy(EntityType type) {
        return false;
    }
    @Override
    protected void attack() {
        if (currentTarget == null)
            return;
        currentTarget.receiveDamage(config.damage);
        HP = 0;
    }
}
