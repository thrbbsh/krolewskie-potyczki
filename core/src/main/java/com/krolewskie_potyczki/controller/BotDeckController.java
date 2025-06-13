package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.view.ArenaView;

public class BotDeckController extends DeckController {
    public static final float RIVER_X_END = GameConfig.getInstance().getZonePointsConstantsConfig().riverXEnd;
    public static final float RIGHT_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().rightBorder;

    public static final int DECK_SIZE = GameConfig.getInstance().getArenaConstants().deckSize;

    public static final float EASY_BOT_SPAWN_SPEED = GameConfig.getInstance().getBotConstants().easyBotSpawnSpeed;
    public static final float MEDIUM_BOT_SPAWN_SPEED = GameConfig.getInstance().getBotConstants().mediumBotSpawnSpeed;
    public static final float HARD_BOT_SPAWN_SPEED = GameConfig.getInstance().getBotConstants().hardBotSpawnSpeed;

    private final float speed;

    private float botSpawn;
    private float botSpawnTimer = 0;

    public BotDeckController(Arena arena, ArenaView arenaView, ArenaController arenaController) {
        super(arena, arenaView, arenaController);
        teamType = TeamType.BOT;

        Preferences prefs = Gdx.app.getPreferences("MyGameSettings");
        int difficulty = prefs.getInteger("difficulty", 2);
        if (difficulty == 1) speed = EASY_BOT_SPAWN_SPEED;
        else if (difficulty == 2) speed = MEDIUM_BOT_SPAWN_SPEED;
        else speed = HARD_BOT_SPAWN_SPEED;

        botSpawn = (speed + (float) (Math.random() * 1f)) / Arena.ELIXIR_SPEED;
    }

    @Override
    public void onCardClicked(int cardIdx) {}

    @Override
    public void onMapTouched(Vector2 pos) {
        if (!deck.someCardIsSelected() ||
            !(RIVER_X_END <= pos.x && pos.x <= RIGHT_BORDER && DOWN_BORDER <= pos.y && pos.y <= UP_BORDER))
            return;
        super.onMapTouched(pos);
    }

    @Override
    public void update(float delta) {
        botSpawnTimer += delta;
        if (botSpawnTimer >= botSpawn) {
            int spawnIdx = (int) (Math.random() % DECK_SIZE);
            deck.setSelectedCardIdx(spawnIdx);
            float spawnX = RIVER_X_END + (float) (Math.random() * (RIGHT_BORDER - RIVER_X_END));
            float spawnY = DOWN_BORDER + (float) (Math.random() * (UP_BORDER - DOWN_BORDER));
            onMapTouched(new Vector2(spawnX, spawnY));
            botSpawn = (speed + (float) (Math.random() * 1f)) / Arena.ELIXIR_SPEED;
            botSpawnTimer = 0f;
        }
    }
}
