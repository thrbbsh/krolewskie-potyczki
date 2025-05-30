package com.krolewskie_potyczki.model;

import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

import java.util.HashMap;
import java.util.Map;

public class EntityFactory {
    public interface SpawnFunction {
        Entity spawn(EntityConfig cfg, boolean isPlayerEntity, float x, float y);
    }

    private final Map<EntityType, SpawnFunction> registry = new HashMap<>();
    private final GameConfig gameConfig;

    public EntityFactory() {
        gameConfig = GameConfig.getInstance();

        register(EntityType.MAIN_TOWER, (cfg, isP, x, y) -> new MainTower(isP, x, y));
        register(EntityType.SIDE_TOWER, (cfg, isP, x, y) -> new SideTower(isP, x, y));
        register(EntityType.TRIANGLE, (cfg, isP, x, y) -> new TriangleUnit(isP, x, y));
        register(EntityType.SQUARE, (cfg, isP, x, y) -> new SquareUnit(isP, x, y));
        register(EntityType.TOMBSTONE, (cfg, isP, x, y) -> new TombstoneUnit(isP, x, y));
        register(EntityType.SKELETON, (cfg, isP, x, y) -> new SkeletonUnit(isP, x, y));
    }

    private void register(EntityType type, SpawnFunction spawnFunction) {
        registry.put(type, spawnFunction);
    }

    public Entity spawnEntity(EntityType type, boolean isPlayerEntity, float x, float y) {
        EntityConfig cfg = gameConfig.getEntityConfig(type);
        if (cfg == null) {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
        SpawnFunction fn = registry.get(type);
        if (fn == null) {
            throw new IllegalStateException("No spawn function for type: " + type);
        }
        return fn.spawn(cfg, isPlayerEntity, x, y);
    }
}
