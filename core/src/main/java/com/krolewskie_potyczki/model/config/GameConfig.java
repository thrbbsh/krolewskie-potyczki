package com.krolewskie_potyczki.model.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class GameConfig {
    private final Map<String, EntityConfig> entities = new HashMap<>();
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
            EntityConfig cfg = json.readValue(EntityConfig.class, entry);
            cfg.type = typeName;
            entities.put(typeName, cfg);
        }
    }

    public EntityConfig getEntityConfig(String typeName) {
        return entities.get(typeName);
    }
}
