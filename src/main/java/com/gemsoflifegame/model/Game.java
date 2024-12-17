package com.gemsoflifegame.model;

public class Game {
    private int[] secretCombination;
    private int attempts;
    private boolean gameOver;

    // Constructor accepting secret combination (int[])
    public Game(int[] secretCombination) {
        this.secretCombination = secretCombination;
        this.attempts = 0;
        this.gameOver = false;
    }

    public int[] getSecretCombination() {
        return secretCombination;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String submitGuess(int[] playerGuess) {
        attempts++;
        int correctNumbers = 0;
        int correctPositions = 0;

        // Check how many numbers are correct and in the correct positions
        for (int i = 0; i < playerGuess.length; i++) {
            if (playerGuess[i] == secretCombination[i]) {
                correctPositions++;
            } else if (contains(secretCombination, playerGuess[i])) {
                correctNumbers++;
            }
        }

        // If all positions are correct, the game is over
        if (correctPositions == secretCombination.length) {
            gameOver = true;
        }

        return String.format("Correct Numbers: %d | Correct Positions: %d", correctNumbers, correctPositions);
    }

    public String provideHint() {
        // For simplicity, return the first digit of the combination as a hint
        return "Hint: The first digit is " + secretCombination[0];
    }

    private boolean contains(int[] array, int value) {
        for (int num : array) {
            if (num == value) {
                return true;
            }
        }
        return false;
    }
}


