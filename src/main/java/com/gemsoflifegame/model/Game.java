package com.gemsoflifegame.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private int[] secretCombination;
    private int guessCount;
    private List<GuessHistory> guessHistory; // Store history of guesses

    public Game(int[] secretCombination) {
        this.secretCombination = secretCombination;
        this.guessCount = 0;
        this.guessHistory = new ArrayList<>();
    }

    // Increment guess count
    public void incrementGuessCount() {
        this.guessCount++;
    }

    public String submitGuess(int[] guess) {
        int correctNumbers = 0;
        int correctPositions = 0;

        // Calculate correct numbers and positions
        for (int i = 0; i < guess.length; i++) {
            if (guess[i] == secretCombination[i]) {
                correctPositions++;
            } else if (contains(secretCombination, guess[i])) {
                correctNumbers++;
            }
        }

        // Create life lesson based on the guess result
        String lifeLesson = getLifeLesson(correctNumbers, correctPositions);

        // Add guess history
        GuessHistory history = new GuessHistory(guess, correctNumbers, correctPositions, lifeLesson);
        guessHistory.add(history);

        // Return formatted result
        return String.format("Guess: %s | Correct Numbers: %d | Correct Positions: %d | Life Lesson: %s",
                history.getFormattedGuess(), correctNumbers, correctPositions, lifeLesson);
    }

    private boolean contains(int[] arr, int value) {
        for (int num : arr) {
            if (num == value) {
                return true;
            }
        }
        return false;
    }

    private String formatGuess(int[] guess) {
        StringBuilder formatted = new StringBuilder();
        for (int num : guess) {
            formatted.append(num).append(" ");
        }
        return formatted.toString().trim();
    }

    private String getLifeLesson(int correctNumbers, int correctPositions) {
        if (correctNumbers == 0 && correctPositions == 0) {
            return "💎 Grit: \"Success is not final, failure is not fatal: It is the courage to continue that counts.\"";
        } else if (correctNumbers == 0) {
            return "✨ Self-Learning: \"The beautiful thing about learning is that no one can take it away from you.\"";
        } else {
            return "🔍 Problem-Solving: \"Don’t find fault, find a remedy.\"";
        }
    }

    public List<GuessHistory> getGuessHistory() {
        return guessHistory;
    }

    public boolean isGameOver() {
        return guessCount >= 10; // Example of maximum attempts
    }

    // Class to represent guess history
    public static class GuessHistory {
        private int[] guess;
        private int correctNumbers;
        private int correctPositions;
        private String lifeLesson;

        public GuessHistory(int[] guess, int correctNumbers, int correctPositions, String lifeLesson) {
            this.guess = guess;
            this.correctNumbers = correctNumbers;
            this.correctPositions = correctPositions;
            this.lifeLesson = lifeLesson;
        }

        public String getFormattedGuess() {
            return new Game(null).formatGuess(guess); // Fix: Call formatGuess from an instance of Game class
        }

        public String getLifeLesson() {
            return lifeLesson;
        }
    }
}
