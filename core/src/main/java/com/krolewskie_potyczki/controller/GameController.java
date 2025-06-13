package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.model.endcondition.DefaultGameEndCondition;
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
    private final DefaultGameEndCondition endCondition;
    private final GameView gameView;

    private Screen gameScreen;
    private PauseScreen pauseScreen;

    private final DeckController playerDeckController;
    private final DeckController botDeckController;

    public GameController(Main game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;

        arenaController = new ArenaController();
        arena = arenaController.getArena();
        endCondition = new DefaultGameEndCondition();

        playerDeckController = new PlayerDeckController(arena, gameView.getArenaView(), arenaController);
        botDeckController = new BotDeckController(arena, gameView.getArenaView(), arenaController);
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
        playerDeckController.update(delta);
        botDeckController.update(delta);
        arenaController.update(delta);
    }

    private void onGameEnded(MatchResult result) {
        game.setScreen(new EndGameScreen(this, result));
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
        playerDeckController.onMapTouched(pos);
    }
}
