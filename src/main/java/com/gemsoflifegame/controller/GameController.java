package com.gemsoflifegame.controller;

import com.gemsoflifegame.model.Game;
import com.gemsoflifegame.model.Guess;
import com.gemsoflifegame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
public class GameController {

    private Game game;
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
        this.game = gameService.startNewGame();  // Starts the game with a random secret combination
    }

    @PostMapping("/guess")
    public String submitPlayerGuess(@RequestBody int[] playerGuess) {
        return game.submitGuess(playerGuess);
    }

    @GetMapping("/isGameOver")
    public boolean isGameOver() {
        return game.isGameOver();
    }

    @GetMapping("/getSecretCombination")
    public int[] getSecretCombination() {
        return game.getSecretCombination();
    }

    @GetMapping("/attemptsRemaining")
    public int getAttemptsRemaining() {
        return game.getAttemptsRemaining();
    }

    @GetMapping("/guessHistory")
    public List<String> getGuessHistory() {
        return game.getGuesses().stream()
                .map(Guess::toString)
                .collect(Collectors.toList());
    }
}

