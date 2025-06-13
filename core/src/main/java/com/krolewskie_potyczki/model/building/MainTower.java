package com.krolewskie_potyczki.model.building;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.projectile.ProjectileSpawnListener;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

import java.util.List;

public class MainTower extends Tower implements UsesProjectiles {
    public MainTower(TeamType teamType, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.MAIN_TOWER), teamType, pos);
    }
    ProjectileSpawnListener listener;
    @Override
    public void setProjectileSpawnListener(ProjectileSpawnListener listener) {
        this.listener = listener;
    }
    @Override
    protected void attack(List<Entity> activeEntities) {
        if (currentTarget != null) {
            listener.onProjectileSpawned(EntityType.SHURIKEN, getTeamType(), getViewPos());
        }
    }
}
