package com.gemsoflifegame.controller;

import com.gemsoflifegame.service.GameService;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final GameService gameService;
    private int correctNumbers;
    private int correctPositions;
    private int[] targetNumber;  // The number the player needs to guess
    private boolean gameOver;
    private List<String> guessHistory;  // To track all guesses and results

    public GameController(GameService gameService) {
        this.gameService = gameService;
        this.gameOver = false;
        this.guessHistory = new ArrayList<>();
    }

    // Start a new game
    public void startNewGame() {
        // Generate a random 4-digit number for the game
        targetNumber = generateRandomNumber();
        this.correctNumbers = 0;
        this.correctPositions = 0;
        this.gameOver = false;
        this.guessHistory.clear();  // Reset guess history when a new game starts
    }

    // Check the guess and return result
    public String checkGuess(int[] guess) {
        this.correctNumbers = calculateCorrectNumbers(guess);
        this.correctPositions = calculateCorrectPositions(guess);

        // Prepare result message
        String result = "Correct Numbers: " + correctNumbers + ", Correct Positions: " + correctPositions;

        // Store the guess and result in history
        guessHistory.add("Guess: " + arrayToString(guess) + " -> " + result);

        // If all numbers are correct and in the correct positions, the game is over
        if (correctPositions == 4) {
            gameOver = true;
            return "Congratulations! You've won the game!";
        }

        return result;
    }

    // Getters for correct numbers and positions
    public int getCorrectNumbers() {
        return correctNumbers;
    }

    public int getCorrectPositions() {
        return correctPositions;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public List<String> getGuessHistory() {
        return guessHistory;  // Return the guess history
    }

    // Calculate how many numbers are correct (but not necessarily in the correct position)
    private int calculateCorrectNumbers(int[] guess) {
        int correctCount = 0;
        for (int i = 0; i < guess.length; i++) {
            for (int j = 0; j < targetNumber.length; j++) {
                if (guess[i] == targetNumber[j]) {
                    correctCount++;
                    break;  // Prevent double counting the same number
                }
            }
        }
        return correctCount;
    }

    // Calculate how many numbers are correct and in the correct position
    private int calculateCorrectPositions(int[] guess) {
        int correctPosCount = 0;
        for (int i = 0; i < guess.length; i++) {
            if (guess[i] == targetNumber[i]) {
                correctPosCount++;
            }
        }
        return correctPosCount;
    }

    // Provide a hint for the player
    public String provideHint() {
        // Return a hint by revealing one correct number at a random position
        int randomPosition = (int) (Math.random() * targetNumber.length);
        return "Hint: The number at position " + (randomPosition + 1) + " is " + targetNumber[randomPosition];
    }

    // Helper method to generate a random 4-digit number
    private int[] generateRandomNumber() {
        int[] number = new int[4];
        for (int i = 0; i < 4; i++) {
            number[i] = (int) (Math.random() * 10); // Generate a random digit between 0 and 9
        }
        return number;
    }

    // Helper method to convert array to a string for easy display
    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i : array) {
            sb.append(i).append(" ");
        }
        return sb.toString().trim();
    }

    // Getter for the target number (to display in the game or use in UI)
    public int[] getTargetNumber() {
        return targetNumber;
    }
}
