package com.gemsoflifegame.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.gemsoflifegame.model.Game;
import com.gemsoflifegame.model.Guess;

import java.net.URI;
import java.util.Arrays;

/**
 * Service class responsible for handling the game logic and interaction with external APIs.
 * Specifically, it starts new games by retrieving a secret combination of numbers and manages the current game state.
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

    // RestTemplate used for making HTTP requests
    private final RestTemplate restTemplate;

    // Holds the current game instance
    private Game currentGame;

    /**
     * Constructor that initializes the GameService with the RestTemplate.
     *
     * @param restTemplate the RestTemplate instance used for HTTP requests
     */
    public GameService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Starts a new game by generating a secret 4-digit number combination
     * via the Random.org API. The number combination is used as the secret
     * for the game.
     *
     * @return the newly started Game object
     * @throws IllegalStateException if there was an issue retrieving the secret combination
     */
    public Game startNewGame() {
        // Build the URI to make the API call to Random.org to generate random integers
        URI uri = URI.create(String.format("%s?num=%d&min=%d&max=%d&col=1&base=10&format=plain&rnd=new",
                API_URL, NUMBERS_TO_GENERATE, MIN_VALUE, MAX_VALUE));

        // Make the GET request to the API and retrieve the response as a string
        String response = restTemplate.getForObject(uri, String.class);

        // Check if the response is empty or invalid
        if (response == null || response.trim().isEmpty()) {
            throw new IllegalStateException("Failed to retrieve valid secret combination from Random.org API.");
        }

        // Convert the response string into an array of integers representing the secret combination
        int[] secretCombination = Arrays.stream(response.split("\n"))
                .mapToInt(Integer::parseInt)
                .toArray();

        // Create a new Game instance with the secret combination and store it in the currentGame field
        currentGame = new Game(secretCombination);

        // Return the newly created game object
        return currentGame;
    }

    /**
     * Returns the current game instance.
     *
     * @return the current active game
     * @throws IllegalStateException if no game is currently active
     */
    public Game getCurrentGame() {
        // Check if the game has been initialized
        if (currentGame == null) {
            throw new IllegalStateException("No game is currently active.");
        }

        // Return the current active game
        return currentGame;
    }
}
