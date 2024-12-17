package com.gemsoflifegame.controller;

import com.gemsoflifegame.model.Game;
import com.gemsoflifegame.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
        this.gameService.startNewGame();  // Start a new game when the controller is created
    }

    @PostMapping("/guess")
    public String checkGuess(@RequestBody int[] playerGuess) {
        return gameService.getCurrentGame().submitGuess(playerGuess);
    }

    @GetMapping("/gameover")
    public boolean isGameOver() {
        return gameService.getCurrentGame().isGameOver();
    }

    @GetMapping("/hint")
    public String provideHint() {
        return gameService.getCurrentGame().provideHint();
    }

    @PostMapping("/new")
    public void startNewGame() {
        gameService.startNewGame();
    }
}
