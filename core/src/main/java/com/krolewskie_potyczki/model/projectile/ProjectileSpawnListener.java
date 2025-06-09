package com.krolewskie_potyczki.model.projectile;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.team.TeamType;

public interface ProjectileSpawnListener {
    void onProjectileSpawned(EntityType projectileType, TeamType teamType, Vector2 pos);
}
