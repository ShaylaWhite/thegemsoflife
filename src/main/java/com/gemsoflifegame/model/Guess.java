package com.gemsoflifegame.model;

/**
 * Represents a single guess made by the player in the game.
 * Contains the player's guess, the number of correct numbers,
 * the number of correct positions, and a life lesson derived
 * from the guess result.
 */
public class Guess {
    private int[] playerGuess;
    private int correctNumbers;
    private int correctPositions;
    private String lifeLesson;

    /**
     * Constructor for creating a Guess instance.
     *
     * @param playerGuess the player's guess (array of numbers)
     * @param correctNumbers the number of digits that are correct
     * @param correctPositions the number of digits in the correct positions
     * @param lifeLesson the life lesson associated with the guess
     */
    public Guess(int[] playerGuess, int correctNumbers, int correctPositions, String lifeLesson) {
        this.playerGuess = playerGuess.clone(); // Clone the array to maintain immutability
        this.correctNumbers = correctNumbers;
        this.correctPositions = correctPositions;
        this.lifeLesson = lifeLesson;
    }

    public int[] getPlayerGuess() {
        return playerGuess.clone(); // Ensure the original array is not modified from outside
    }

    public int getCorrectNumbers() {
        return correctNumbers;
    }

    public int getCorrectPositions() {
        return correctPositions;
    }

    public String getLifeLesson() {
        return lifeLesson;
    }

    public String getFormattedGuess() {
        // Convert the playerGuess array to a string format for display
        StringBuilder formattedGuess = new StringBuilder();
        for (int num : playerGuess) {
            formattedGuess.append(num).append(" ");
        }
        return formattedGuess.toString().trim(); // Return the guess as a string
    }
}
