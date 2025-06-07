package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.model.endcondition.DefaultGameEndCondition;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.screens.EndGameScreen;
import com.krolewskie_potyczki.screens.MenuScreen;
import com.krolewskie_potyczki.screens.PauseScreen;
import com.krolewskie_potyczki.model.result.MatchResult;
import com.krolewskie_potyczki.view.GameView;

public class GameController {
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
        enemySpawn = (5f + (float) (Math.random() * 1f)) / arena.getElixirSpeed();

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
            EntityType type;
            double spawnChance = Math.random();
            if (spawnChance < 0.2) type = EntityType.SQUARE;
            else if (spawnChance < 0.4) type = EntityType.TRIANGLE;
            else if (spawnChance < 0.6) type = EntityType.SKELETON_ARMY;
            else if (spawnChance < 0.8) type = EntityType.TOMBSTONE;
            else type = EntityType.INFERNO;
            float spawnX = 1200f + (float) (Math.random() * 550f);
            float spawnY = 250f + (float) (Math.random() * 750f);
            arenaController.spawnEntity(type, false, new Vector2(spawnX, spawnY));
            enemySpawn = (5f + (float) (Math.random() * 1f)) / arena.getElixirSpeed();
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
