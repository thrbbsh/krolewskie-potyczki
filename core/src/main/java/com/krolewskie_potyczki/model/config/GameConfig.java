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
}
