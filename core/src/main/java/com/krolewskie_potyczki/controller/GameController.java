package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.endcondition.DefaultGameEndCondition;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.screens.EndGameScreen;
import com.krolewskie_potyczki.screens.MenuScreen;
import com.krolewskie_potyczki.screens.PauseScreen;
import com.krolewskie_potyczki.model.result.MatchResult;
import com.krolewskie_potyczki.view.GameView;

import java.util.List;

public class GameController {
    public static final float RIVER_X_END = GameConfig.getInstance().getZonePointsConstantsConfig().riverXEnd;
    public static final float RIGHT_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().rightBorder;
    public static final float UP_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().upBorder;
    public static final float DOWN_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().downBorder;

    public static final float BASIC_RANDOM_ENEMY_SPAWN = GameConfig.getInstance().getEnemyConstants().basicRandomEnemySpawn;
    public static final List<EntityType> SPAWN_LIST = GameConfig.getInstance().getDeckConstants().spawnList;

    private final Main game;
    private final ArenaController arenaController;
    private final Arena arena;
    private boolean paused = false;
    private float enemySpawn;
    private float enemySpawnTimer = 0;
    private final DefaultGameEndCondition endCondition;
    private final GameView gameView;

    private Screen gameScreen;
    private PauseScreen pauseScreen;

    private final DeckController deckController;

    public GameController(Main game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;

        arenaController = new ArenaController();
        arena = arenaController.getArena();
        endCondition = new DefaultGameEndCondition();
        enemySpawn = (5f + (float) (Math.random() * 1f)) / Arena.ELIXIR_SPEED;

        deckController = new DeckController(arena, gameView.getArenaView(), arenaController);
    }

    public void update(float delta) {
        if (isPaused()) {
            pauseScreen.getView().render(delta);
            return;
        }

        if (endCondition.isGameOver(arena)) {
            gameView.pause();
            onGameEnded(endCondition.calculateResult(arena));
        }

        gameView.renderGame(delta, arena.getPlayerElixir(), arena.getMaxElixir(), arena.getTimeLeft(), arena.getActiveEntities());
        deckController.update(delta);
        arenaController.update(delta);
        enemyMove(delta);
    }

    private void onGameEnded(MatchResult result) {
        game.setScreen(new EndGameScreen(this, result));
    }

    private void enemyMove(float delta) {
        enemySpawnTimer += delta;
        if (enemySpawnTimer >= enemySpawn) {
            EntityType type = null;
            double spawnChance = Math.random();
            for (int i = 1; i <= SPAWN_LIST.size(); i++)
                if (spawnChance <= (float) i / (float) SPAWN_LIST.size()) {
                    type = SPAWN_LIST.get(i - 1);
                    break;
                }
            float spawnX = RIVER_X_END + (float) (Math.random() * (RIGHT_BORDER - RIVER_X_END));
            float spawnY = DOWN_BORDER + (float) (Math.random() * (UP_BORDER - DOWN_BORDER));
            arenaController.spawnEntity(type, TeamType.BOT, new Vector2(spawnX, spawnY));
            enemySpawn = (BASIC_RANDOM_ENEMY_SPAWN + (float) (Math.random() * 1f)) / Arena.ELIXIR_SPEED;
            enemySpawnTimer  = 0f;
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void onPauseClicked() {
        paused = true;
        gameView.pause();
        gameScreen = game.getScreen();
        pauseScreen = new PauseScreen(this);
        game.setScreen(pauseScreen);
    }

    public void onResumeClicked() {
        paused = false;
        gameView.resume();
        game.setScreen(gameScreen);
    }

    public void onMenuClicked() {
        game.setScreen(new MenuScreen(game));
    }

    public void onMapTouched(Vector2 pos) {
        deckController.onMapTouched(pos);
    }
}
