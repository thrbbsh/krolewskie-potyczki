package com.krolewskie_potyczki.model.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.EnumMap;
import java.util.Map;

public class GameConfig {
    private final Map<EntityType, EntityConfig> entities = new EnumMap<>(EntityType.class);
    private static GameConfig instance;
    private GameConstantsConfig gameConstants;
    private ArenaConstantsConfig arenaConstants;
    private EntityConstantsConfig entityConstants;
    private CompositeUnitConstantsConfig compositeUnitConstants;
    private ZonePointsConstantsConfig zonePointsConstants;
    private PhysicsWorldConstantsConfig physicsWorldConstants;

    private GameConfig() { }

    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
            instance.loadConfig();
        }
        return instance;
    }

    private void loadConfig() {
        Json json = new Json();
        JsonValue root = new JsonReader().parse(Gdx.files.internal("config.json"));
        if (root == null)
            throw new RuntimeException("Config file not found");

        JsonValue gameConsts = root.get("gameConstants");
        if (gameConsts == null)
            throw new RuntimeException("'constants' section not found in config.json");
        gameConstants = json.readValue(GameConstantsConfig.class, gameConsts);

        JsonValue arenaConsts = root.get("arenaConstants");
        if (arenaConsts == null)
            throw new RuntimeException("'constants' section not found in config.json");
        arenaConstants = json.readValue(ArenaConstantsConfig.class, arenaConsts);

        JsonValue compositeUnitConsts = root.get("compositeUnitConstants");
        if (compositeUnitConsts == null)
            throw new RuntimeException("'constants' section not found in config.json");
        compositeUnitConstants = json.readValue(CompositeUnitConstantsConfig.class, compositeUnitConsts);

        JsonValue entityConsts = root.get("entityConstants");
        if (entityConsts == null)
            throw new RuntimeException("'constants' section not found in config.json");
        entityConstants = json.readValue(EntityConstantsConfig.class, entityConsts);

        JsonValue zonePointsConsts = root.get("zonePointsConstants");
        if (zonePointsConsts == null)
            throw new RuntimeException("'constants' section not found in config.json");
        zonePointsConstants = json.readValue(ZonePointsConstantsConfig.class, zonePointsConsts);

        JsonValue physicsWorldConsts = root.get("physicsWorldConstants");
        if (physicsWorldConsts == null)
            throw new RuntimeException("'constants' section not found in config.json");
        physicsWorldConstants = json.readValue(PhysicsWorldConstantsConfig.class, physicsWorldConsts);

        JsonValue ents = root.get("entities");
        for (JsonValue entry : ents) {
            String typeName = entry.name();
            EntityType type;
            try {
                type = EntityType.valueOf(typeName);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Unknown entity type in config: " + typeName, e);
            }

            EntityConfig cfg = json.readValue(EntityConfig.class, entry);
            cfg.type = type;
            entities.put(type, cfg);
        }
    }

    public EntityConfig getEntityConfig(EntityType typeName) {
        return entities.get(typeName);
    }

    public GameConstantsConfig getGameConstants() {
        return gameConstants;
    }

    public ArenaConstantsConfig getArenaConstants() {
        return arenaConstants;
    }

    public EntityConstantsConfig getEntityConstants() {
        return entityConstants;
    }

    public CompositeUnitConstantsConfig getCompositeUnitConstantsConfig() {
        return compositeUnitConstants;
    }

    public ZonePointsConstantsConfig getZonePointsConstantsConfig() {
        return zonePointsConstants;
    }

    public PhysicsWorldConstantsConfig getPhysicsWorldConstants() {
        return physicsWorldConstants;
    }
}
