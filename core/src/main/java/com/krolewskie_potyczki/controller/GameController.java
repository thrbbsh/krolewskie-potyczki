package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.DefaultGameEndCondition;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.screens.EndGameScreen;
import com.krolewskie_potyczki.screens.MenuScreen;
import com.krolewskie_potyczki.screens.PauseScreen;
import com.krolewskie_potyczki.view.CardView;
import com.krolewskie_potyczki.model.MatchResult;
import com.krolewskie_potyczki.view.GameView;

public class GameController {
    private final Arena arena;
    private final Main game;
    private final ArenaController arenaController;
    private boolean paused = false;
    private float EnemySpawn;
    private float EnemySpawnTimer = 0;
    private CardView selectedCard;
    private final DefaultGameEndCondition endCondition;
    private final GameView gameView;

    private Screen gameScreen;
    private PauseScreen pauseScreen;

    public GameController(Main game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        arena = new Arena();

        arenaController = new ArenaController(arena);
        EnemySpawn = (5f + (float) (Math.random() * 1f)) / arena.getElixirSpeed();
        endCondition = new DefaultGameEndCondition();
    }

    public void update(float delta) {
        if (isPaused()) {
            pauseScreen.getView().render(delta);
            return;
        }

        arenaController.update(delta);
        arena.updateTimer(-delta);
        enemyMove(delta);

        if (endCondition.isGameOver(arena)) {
            gameView.pause();
            onGameEnded(endCondition.calculateResult(arena));
        }

        System.out.println(arena.getTimeLeft());

        gameView.renderGame(delta, arena.getPlayerElixir(), arena.getMaxElixir(), arena.getTimeLeft(), arena.getActiveEntities());
    }

    private void onGameEnded(MatchResult result) {
        game.setScreen(new EndGameScreen(this, result));
    }

    private void enemyMove(float delta) {
        EnemySpawnTimer += delta;
        if (EnemySpawnTimer >= EnemySpawn) {
            EntityType type;
            double spawnChance = Math.random();
            if (spawnChance < 0.25) type = EntityType.SQUARE;
            else if (spawnChance < 0.5) type = EntityType.TRIANGLE;
            else if (spawnChance < 0.75) type = EntityType.SKELETON_ARMY;
            else type = EntityType.TOMBSTONE;
            float spawnX = 1200f + (float) (Math.random() * 550f);
            float spawnY = 250f + (float) (Math.random() * 750f);
            arenaController.spawnEntity(type, false, new Vector2(spawnX, spawnY));
            EnemySpawn = (5f + (float) (Math.random() * 1f)) / arena.getElixirSpeed();
            EnemySpawnTimer  = 0f;
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

    public void onSpawnEntityClicked(CardView cardView) {
        if (arena.getPlayerElixir() < cardView.getCard().getElixirCost())
            return;

        if (cardView == selectedCard) {
            cardView.setSelected(false);
            selectedCard = null;
        } else {
            if (selectedCard != null) {
                selectedCard.setSelected(false);
            }
            selectedCard = cardView;
            cardView.setSelected(true);
        }
    }

    public CardView getSelectedCardView() {
        return selectedCard;
    }

    public boolean onMapTouched(Vector2 pos) {
        if (selectedCard == null || arena.getPlayerElixir() < selectedCard.getCard().getElixirCost() ||
            !(287 <= pos.x && pos.x <= 1027 && 227 <= pos.y && pos.y <= 1062))
            return false;
        arenaController.spawnEntity(selectedCard.getCard().getEntityType(), true, pos);
        selectedCard.setSelected(false);
        arena.spendElixir(selectedCard.getCard().getElixirCost());
        selectedCard = null;

        return true;
    }
}
