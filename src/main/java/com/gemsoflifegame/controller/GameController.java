package com.gemsoflifegame.controller;

import com.gemsoflifegame.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/game")
public class GameController {

    private Game game;

    public GameController() {
        // Initialize the game with a random secret combination (for example)
        this.game = startNewGame();
    }

    // Method to start a new game with a random secret combination
    public Game startNewGame() {
        Random random = new Random();
        int[] secretCombination = new int[4];
        for (int i = 0; i < 4; i++) {
            secretCombination[i] = random.nextInt(8);  // Numbers between 0 and 7
        }
        return new Game(secretCombination);
    }

    // Method to submit a player's guess
    public String submitPlayerGuess(int[] playerGuess) {
        return game.submitGuess(playerGuess);
    }

    // Method to check if the game is over
    public boolean isGameOver() {
        return game.isGameOver();
    }

    // Method to get the secret combination (for feedback after the game ends)
    public int[] getSecretCombination() {
        return game.getSecretCombination();
    }
}
