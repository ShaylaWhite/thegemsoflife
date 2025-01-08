package com.gemsoflifegame;

import com.gemsoflifegame.model.Guess;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the game logic of a number-guessing game.
 * It handles the secret combination, user guesses, and tracks game history.
 * Game State Management
 */
public class Game {
    private int[] secretCombination;
    private int guessCount;
    private List<Guess> guessHistory;   //

    // Array of motivational gems and corresponding life lessons
    private static final String[] LIFE_GEMS = {
            "💎 Grit",
            "✨ Self-Learning",
            "🔍 Problem-Solving",
            "💪 Perseverance",
            "🔥 Passion",
            "🌟 Self-Worth",
            "🌈 Belief in Yourself",
            "🌱 Uniqueness",
            "🌻 Resilience",
            "🧠 Growth"
    };

    private static final String[] LIFE_LESSONS = {
            "\"Success is not final, failure is not fatal: It is the courage to continue that counts.\"",
            "\"The beautiful thing about learning is that no one can take it away from you.\"",
            "\"Don’t find fault, find a remedy.\"",
            "\"It does not matter how slowly you go as long as you do not stop.\"",
            "\"Passion is energy. Feel the power that comes from focusing on what excites you.\"",
            "\"You are enough, just as you are.\"",
            "\"Believe you can and you're halfway there.\"",
            "\"Don't be afraid to be unique, embrace it!\"",
            "\"It's not how far you fall, but how high you bounce that counts.\"",
            "\"The only way to do great work is to love what you do.\""
    };

    /**
     * Constructs a Game object with a secret combination.
     * The constructor initializes the secret combination and sets up a history log for guesses.
     * Using a list (ArrayList) ensures scalability for tracking guesses
     *
     * @param secretCombination the secret combination that players must guess
     */
    public Game(int[] secretCombination) {
        this.secretCombination = secretCombination;
        this.guessCount = 0;
        this.guessHistory = new ArrayList<>();
    }

    /**
     * Retrieves the secret combination for the game.
     *
     * @return the secret combination
     */
    public int[] getSecretCombination() {
        return secretCombination;
    }

    /**
     * Increments the guess count each time a player submits a guess.
     */
    public void incrementGuessCount() {
        this.guessCount++;
    }

    /**
     * Submits a player's guess and checks how many numbers are correct and in the correct positions.
     * The method also provides a life lesson and gem for motivation based on the player's performance.
     *
     * @param guess the player's guess
     * @return a formatted string with the result of the guess, life lesson, and gem dropped
     */
    public String submitGuess(int[] guess) {
        // Get the number of correct positions and numbers
        int correctNumbers = getCorrectNumbers(guess);
        int correctPositions = getCorrectPositions(guess);

        // Get a motivational life lesson based on the guess accuracy
        String lifeLesson = getLifeLesson(correctNumbers, correctPositions);

        // Determine the gem dropped and the corresponding life gem
        String gemDropped = LIFE_GEMS[guessCount % LIFE_GEMS.length];
        String lifeGem = LIFE_LESSONS[guessCount % LIFE_LESSONS.length];

        // Create a new Guess object and add it to the guess history
        Guess newGuess = new Guess(guess, correctNumbers, correctPositions, lifeLesson);
        guessHistory.add(newGuess);

        // Return the result as a formatted string
        return String.format("Your Guess: %s\nCorrect Numbers: %d\nCorrect Positions: %d\nLife Lesson: 🌟 %s\nGem Dropped: \"%s\"\nLife Gem Gained: \"%s\"",
                newGuess.getFormattedGuess(), correctNumbers, correctPositions, lifeLesson, gemDropped, lifeGem);
    }

    /**
     * Calculates the number of correct numbers in the guess that are in the wrong positions.
     *
     * @param guess the player's guess
     * @return the number of correct numbers in the wrong positions
     */
    public int getCorrectNumbers(int[] guess) {
        int correctNumbers = 0;
        // Loop through the guess to check for correct numbers
        for (int i = 0; i < guess.length; i++) {
            if (contains(secretCombination, guess[i]) && secretCombination[i] != guess[i]) {
                correctNumbers++;
            }
        }
        return correctNumbers;
    }

    /**
     * Calculates the number of correct numbers that are in the correct positions.
     *
     * @param guess the player's guess
     * @return the number of correct numbers in the correct positions
     */
    public int getCorrectPositions(int[] guess) {
        int correctPositions = 0;
        // Loop through the guess to check for correct positions
        for (int i = 0; i < guess.length; i++) {
            if (guess[i] == secretCombination[i]) {
                correctPositions++;
            }
        }
        return correctPositions;
    }

    /**
     * Checks if a value is present in the array.
     *
     * @param arr the array to check
     * @param value the value to check for
     * @return true if the value is present in the array, false otherwise
     */
    private boolean contains(int[] arr, int value) {
        for (int num : arr) {
            if (num == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a motivational life lesson based on the number of correct guesses.
     *
     * @param correctNumbers the number of correct numbers
     * @param correctPositions the number of correct positions
     * @return a motivational life lesson
     */
    public String getLifeLesson(int correctNumbers, int correctPositions) {
        if (correctNumbers == 0 && correctPositions == 0) {
            return LIFE_LESSONS[0];
        } else if (correctNumbers == 0) {
            return LIFE_LESSONS[1];
        } else {
            return LIFE_LESSONS[2];
        }
    }

    /**
     * Retrieves the history of guesses made in the game.
     *
     * @return a list of Guess objects representing the guess history
     */
    public List<Guess> getGuessHistory() {
        return guessHistory;
    }

    /**
     * Checks if the game is over based on the number of guesses made.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return guessCount >= 10;
    }

    /**
     * Checks if the player's guess matches the secret combination exactly.
     *
     * @param guess the player's guess
     * @return true if the guess is correct, false otherwise
     */
    public boolean isCorrectGuess(int[] guess) {
        for (int i = 0; i < secretCombination.length; i++) {
            if (secretCombination[i] != guess[i]) {
                return false;
            }
        }
        return true;
    }
}
