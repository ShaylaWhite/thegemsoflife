package com.gemsoflifegame.controller;

import com.gemsoflifegame.model.Game;
import com.gemsoflifegame.service.GameService;
import java.util.Arrays;

public class GameController {
    private Game game;
    private GameService gameService;

    public GameController() {
        gameService = new GameService();
        this.game = gameService.startNewGame(); // Start a new game
    }

    public String submitPlayerGuess(int[] playerGuess) {
        return game.submitGuess(playerGuess);
    }

    public int getAttemptsRemaining() {
        return game.getAttemptsRemaining();
    }

    public int[] getSecretCombination() {
        return game.getSecretCombination();
    }

    public boolean isGameOver() {
        return game.getAttemptsRemaining() <= 0;
    }
}

