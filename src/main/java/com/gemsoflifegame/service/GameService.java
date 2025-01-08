package com.gemsoflifegame.service;

import com.gemsoflifegame.Game; // Correct import for Game
import com.gemsoflifegame.model.Guess; // Correct import for Guess

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

/**
 * Service class responsible for handling the game logic and interaction with external APIs.
 */
@Service
public class GameService {

    // API URL to retrieve random numbers
    private static final String API_URL = "https://www.random.org/integers";

    // Number of digits to generate for the secret combination
    private static final int NUMBERS_TO_GENERATE = 4;

    // Minimum and maximum values for the generated numbers
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 7;

    // RestTemplate used for making HTTP requests, simplifies the process of making HTTP requests and handling responses.
    private final RestTemplate restTemplate;

    // Holds the current game instance
    // This design ensures that the game state is kept in a single object, which is easy to manage and access.
    private Game currentGame;

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

        currentGame = new Game(secretCombination);

        return currentGame;
    }

    //
    public Game getCurrentGame() {
        if (currentGame == null) {
            throw new IllegalStateException("No game is currently active.");
        }

        return currentGame;
    }
}

