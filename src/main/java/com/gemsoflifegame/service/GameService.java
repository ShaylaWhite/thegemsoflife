package com.gemsoflifegame.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.gemsoflifegame.model.Game;

import java.net.URI;
import java.util.Arrays;

@Service
public class GameService {

    private static final String API_URL = "https://www.random.org/integers";
    private static final int NUMBERS_TO_GENERATE = 4;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 7;

    private final RestTemplate restTemplate;
    private Game currentGame;  // Store the current game instance

    public GameService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Game startNewGame() {
        URI uri = URI.create(String.format("%s?num=%d&min=%d&max=%d&col=1&base=10&format=plain&rnd=new",
                API_URL, NUMBERS_TO_GENERATE, MIN_VALUE, MAX_VALUE));

        String response = restTemplate.getForObject(uri, String.class);

        if (response == null || response.trim().isEmpty()) {
            throw new IllegalStateException("Failed to retrieve valid secret combination from Random.org API.");
        }

        int[] secretCombination = Arrays.stream(response.split("\n"))
                .mapToInt(Integer::parseInt)
                .toArray();

        currentGame = new Game(secretCombination);  // Store the generated game
        return currentGame;
    }

    public Game getCurrentGame() {
        if (currentGame == null) {
            throw new IllegalStateException("No game is currently active.");
        }
        return currentGame;
    }
}



