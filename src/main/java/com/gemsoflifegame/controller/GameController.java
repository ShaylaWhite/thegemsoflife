package com.gemsoflifegame.controller;

import com.gemsoflifegame.Game; // Correct import for Game
import com.gemsoflifegame.model.Guess; // Correct import for Guess
import com.gemsoflifegame.service.GameService; // Assuming GameService is used here

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller class for managing the game logic and interactions with the game service.
 * The game state is managed using class-level variables allowing the controller to maintain the state of a single game session.
 * delegates core game logic (like generating random numbers) to the GameService, ensuring a clean separation between business logic and user-facing logic.
 * MVC (Model-View-Controller) pattern, where the controller manages the flow of data between the model (game logic) and the user interface.
 */
public class GameController {
    private final GameService gameService; // Service that handles the game operations
    private int[] targetNumber; // The secret number combination to be guessed
    private boolean gameOver; // Flag to indicate if the game is over
    private List<Guess> guessHistory; // List to store all the guesses made during the game
    private int guessCount; // Counter to track the number of guesses made

    //Private fields and methods ensure encapsulation, exposing only the necessary functionality via public methods.

    // Array of life gems, each corresponding to a quote and a life lesson based on the number of correct positions

    private static final String[] LIFE_GEMS = {
            "💎 Grit: \"Success is not final, failure is not fatal: It is the courage to continue that counts.\"",
            "✨ Self-Learning: \"The beautiful thing about learning is that no one can take it away from you.\"",
            "🔍 Problem-Solving: \"Don’t find fault, find a remedy.\"",
            "💪 Perseverance: \"It does not matter how slowly you go as long as you do not stop.\"",
            "🔥 Passion: \"Passion is energy. Feel the power that comes from focusing on what excites you.\"",
            "🌟 Self-Worth: \"You are enough, just as you are.\"",
            "🌈 Belief in Yourself: \"Believe you can and you're halfway there.\"",
            "🌱 Uniqueness: \"Don't be afraid to be unique, embrace it!\"",
            "🌻 Resilience: \"It's not how far you fall, but how high you bounce that counts.\"",
            "🧠 Growth: \"The only way to do great work is to love what you do.\""
    };

    /**
     * Constructor to initialize the game controller.
     *
     * @param gameService the service responsible for handling game logic
     */
    public GameController(GameService gameService) {
        this.gameService = gameService;
        this.gameOver = false;
        this.guessHistory = new ArrayList<>();
        this.guessCount = 0;
        this.targetNumber = new int[4]; // Ensure targetNumber is initialized with a valid size.
    }

    /**
     * Starts a new game by generating a random number combination and resetting the game state.
     */
    public void startNewGame() {
        Game game = gameService.startNewGame(); // Generate the random number from the GameService
        targetNumber = game.getSecretCombination(); // Get the secret combination from the game
        this.gameOver = false;
        this.guessHistory.clear();
        this.guessCount = 0;
    }

    /**
     * Processes a user's guess and checks it against the target number.
     *
     * @param guess the user's guess as an array of integers
     * @return a message indicating the number of correct positions and correct numbers
     */
    public String checkGuess(int[] guess) {
        // Ensure targetNumber is not null or empty before processing the guess
        if (targetNumber == null || targetNumber.length == 0) {
            return "Game not started yet. Please start a new game.";
        }

        int correctPositions = 0;
        int correctNumbers = 0;
        List<Integer> targetList = new ArrayList<>();
        List<Integer> guessList = new ArrayList<>();

        // Find numbers at correct positions
        for (int i = 0; i < targetNumber.length; i++) {
            if (guess[i] == targetNumber[i]) {
                correctPositions++;
            } else {
                targetList.add(targetNumber[i]);
                guessList.add(guess[i]);
            }
        }

        // Check remaining numbers (wrong positions) for correct values
        for (int i = 0; i < guessList.size(); i++) {
            if (targetList.contains(guessList.get(i))) {
                correctNumbers++;
                targetList.remove((Integer) guessList.get(i));
            }
        }

        // Create Guess object and add it to history
        Guess newGuess = new Guess(guess, correctNumbers, correctPositions, LIFE_GEMS[correctPositions]);
        guessHistory.add(newGuess);
        guessCount++;

        return "Correct Numbers: " + correctNumbers + ", Correct Positions: " + correctPositions;
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over or the guess count reaches 10, false otherwise
     */
    public boolean isGameOver() {
        return gameOver || guessCount >= 10;
    }

    /**
     * Checks if the user's guess is correct.
     *
     * @param guess the user's guess as an array of integers
     * @return true if the guess matches the target number, false otherwise
     */
    public boolean isCorrectGuess(int[] guess) {
        return Arrays.equals(guess, targetNumber);
    }

    /**
     * Retrieves the history of all guesses made in the game.
     *
     * @return a list of Guess objects representing the user's guesses
     */
    public List<Guess> getGuessHistory() {
        return guessHistory;
    }

    /**
     * Retrieves the current target number for the game.
     *
     * @return the current secret number combination
     */
    public int[] getCurrentGameSecretCombination() {
        return targetNumber;
    }

    /**
     * Submits a guess and processes it.
     * This is a placeholder method and may be used to trigger game logic or actions on submission.
     *
     * @param guess the user's guess as an array of integers
     */
    public String submitGuess(int[] guess) {
        // Call checkGuess to evaluate the guess
        return checkGuess(guess);
    }

    /**
     * Retrieves the current game instance.
     *
     * @return the current Game object
     */
    public Game getCurrentGame() {
        return gameService.getCurrentGame(); // Assuming GameService has this method
    }
}
