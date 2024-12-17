package com.gemsoflifegame.service;

import org.springframework.stereotype.Service;
import com.gemsoflifegame.model.Game;

@Service
public class GameService {

    public Game startNewGame() {
        int[] secretCombination = generateSecretCombination();
        return new Game(secretCombination);
    }

    private int[] generateSecretCombination() {
        // You can retain your Random.org API logic here or use any mock data.
        return new int[] {1, 4, 2, 3}; // Mocked data for simplicity.
    }
}
