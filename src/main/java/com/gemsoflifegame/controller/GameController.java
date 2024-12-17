package com.gemsoflifegame.controller;

import com.gemsoflifegame.model.Game;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/game")
public class GameController {

    private Game game;

    // No-argument constructor that initializes the game
    public GameController() {
        this.game = startNewGame();  // Start a new game on controller instantiation
    }

    private Game startNewGame() {
        Random random = new Random();
        int[] secretCombination = new int[4];
        for (int i = 0; i < 4; i++) {
            secretCombination[i] = random.nextInt(8); // Numbers between 0 and 7
        }
        return new Game(secretCombination);
    }

    // Endpoint to submit a player's guess
    @PostMapping("/submitGuess")
    public String submitPlayerGuess(@RequestBody int[] playerGuess) {
        return game.submitGuess(playerGuess);
    }

    // Endpoint to check if the game is over
    @GetMapping("/isGameOver")
    public boolean isGameOver() {
        return game.isGameOver();
    }

    // Endpoint to retrieve the secret combination (for testing or debugging)
    @GetMapping("/secretCombination")
    public int[] getSecretCombination() {
        return game.getSecretCombination();
    }
}
