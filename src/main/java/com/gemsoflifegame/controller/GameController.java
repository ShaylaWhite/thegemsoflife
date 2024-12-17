package com.gemsoflifegame.controller;

import com.gemsoflifegame.model.Game;
import com.gemsoflifegame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    private Game game;

    @PostMapping("/start")
    public String startNewGame() {
        game = gameService.startNewGame();
        return "Game started! You have " + game.getAttemptsRemaining() + " attempts.";
    }

    @PostMapping("/guess")
    public String submitGuess(@RequestBody int[] playerGuess) {
        if (game == null) {
            return "Please start a new game first.";
        }

        String feedback = game.submitGuess(playerGuess);
        return feedback;
    }

    @GetMapping("/status")
    public String getGameStatus() {
        if (game == null) {
            return "Please start a new game first.";
        }
        return "Attempts remaining: " + game.getAttemptsRemaining() + "\n" +
                "Guesses: " + game.getGuesses();
    }
}
