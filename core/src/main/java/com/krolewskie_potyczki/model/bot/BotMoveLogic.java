package com.krolewskie_potyczki.model.bot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.model.entity.Deck;
import com.krolewskie_potyczki.model.team.TeamType;

import java.util.List;

public class BotMoveLogic {
    public static final float EASY_BOT_SPAWN_SPEED = GameConfig.getInstance().getBotConstants().easyBotSpawnSpeed;
    public static final float MEDIUM_BOT_SPAWN_SPEED = GameConfig.getInstance().getBotConstants().mediumBotSpawnSpeed;
    public static final float HARD_BOT_SPAWN_SPEED = GameConfig.getInstance().getBotConstants().hardBotSpawnSpeed;

    public static final float UP_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().upBorder;
    public static final float DOWN_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().downBorder;
    public static final float RIVER_X_END = GameConfig.getInstance().getZonePointsConstantsConfig().riverXEnd;
    public static final float RIGHT_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().rightBorder;

    public static final List<EntityType> SPAWN_LIST = GameConfig.getInstance().getDeckConstants().spawnList;

    private final float speed;

    private float botSpawn;
    private float botSpawnTimer = 0;

    private final Deck deck;
    public final Arena arena;

    public BotMoveLogic(Arena arena) {
        this.arena = arena;

        deck = new Deck(SPAWN_LIST);

        Preferences prefs = Gdx.app.getPreferences("MyGameSettings");
        int difficulty = prefs.getInteger("difficulty", 2);
        if (difficulty == 1) speed = EASY_BOT_SPAWN_SPEED;
        else if (difficulty == 2) speed = MEDIUM_BOT_SPAWN_SPEED;
        else speed = HARD_BOT_SPAWN_SPEED;

        botSpawn = (speed + (float) (Math.random() * 1f)) / arena.getElixirSpeed();
    }

    public void update(float delta, TriConsumer<EntityType, TeamType, Vector2> spawn) {
        botSpawnTimer += delta;
        if (botSpawnTimer >= botSpawn) {
            int spawnIdx = (int) (Math.random() * Deck.DECK_SIZE);
            deck.setSelectedCardIdx(spawnIdx);
            float spawnX = RIVER_X_END + (float) (Math.random() * (RIGHT_BORDER - RIVER_X_END));
            float spawnY = DOWN_BORDER + (float) (Math.random() * (UP_BORDER - DOWN_BORDER));
            spawn.accept(deck.getSelectedCardEntityType(), TeamType.BOT, new Vector2(spawnX, spawnY));
            deck.selectedCardWasChosen();
            botSpawn = (speed + (float) (Math.random() * 1f)) / arena.getElixirSpeed();
            botSpawnTimer = 0f;
        }
    }
}
