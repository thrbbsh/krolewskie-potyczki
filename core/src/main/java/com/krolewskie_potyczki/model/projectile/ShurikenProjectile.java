package com.krolewskie_potyczki.model.projectile;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.team.TeamType;

public class ShurikenProjectile extends Projectile {
    public ShurikenProjectile(TeamType teamType, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.SHURIKEN), teamType, pos);
    }
}
