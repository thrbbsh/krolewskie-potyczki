package com.krolewskie_potyczki.model;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

import java.util.HashMap;
import java.util.Map;

public class EntityFactory {
    public interface SpawnFunction {
        Entity spawn(EntityConfig cfg, boolean isPlayerEntity, Vector2 pos);
    }

    private final Map<EntityType, SpawnFunction> registry = new HashMap<>();
    private final GameConfig gameConfig;

    public EntityFactory() {
        gameConfig = GameConfig.getInstance();

        register(EntityType.MAIN_TOWER, (cfg, isP, pos) -> new MainTower(isP, pos));
        register(EntityType.SIDE_TOWER, (cfg, isP, pos) -> new SideTower(isP, pos));
        register(EntityType.TRIANGLE, (cfg, isP, pos) -> new TriangleUnit(isP, pos));
        register(EntityType.SQUARE, (cfg, isP, pos) -> new SquareUnit(isP, pos));
        register(EntityType.TOMBSTONE, (cfg, isP, pos) -> new Tombstone(isP, pos));
        register(EntityType.SKELETON, (cfg, isP, pos) -> new SkeletonUnit(isP, pos));
    }

    private void register(EntityType type, SpawnFunction spawnFunction) {
        registry.put(type, spawnFunction);
    }

    public Entity spawnEntity(EntityType type, boolean isPlayerEntity, Vector2 pos) {
        EntityConfig cfg = gameConfig.getEntityConfig(type);
        if (cfg == null) {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
        SpawnFunction fn = registry.get(type);
        if (fn == null) {
            throw new IllegalStateException("No spawn function for type: " + type);
        }
        return fn.spawn(cfg, isPlayerEntity, pos);
    }
}
