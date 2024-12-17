package com.gemsoflifegame.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int[] secretCombination;
    private int attemptsRemaining;
    private List<Guess> guesses;

    public Game(int[] secretCombination) {
        this.secretCombination = secretCombination;
        this.attemptsRemaining = 10;
        this.guesses = new ArrayList<>();
    }

    public String submitGuess(int[] playerGuess) {
        if (attemptsRemaining <= 0) {
            return "Game Over! No attempts remaining.";
        }

        attemptsRemaining--;
        int correctNumbers = 0;
        int correctPositions = 0;

        for (int i = 0; i < playerGuess.length; i++) {
            if (playerGuess[i] == secretCombination[i]) {
                correctPositions++;
            } else if (contains(secretCombination, playerGuess[i])) {
                correctNumbers++;
            }
        }

        String lifeLesson = getLifeLessonForGuess();
        Guess guess = new Guess(playerGuess, correctNumbers, correctPositions, lifeLesson);
        guesses.add(guess);

        return getFeedback(correctNumbers, correctPositions, lifeLesson);
    }

    private boolean contains(int[] array, int number) {
        for (int n : array) {
            if (n == number) {
                return true;
            }
        }
        return false;
    }

    private String getLifeLessonForGuess() {
        String[] lessons = {"Grit", "Self-Learning", "Problem-Solving", "Perseverance", "Passion", "Self-Worth", "Belief in Yourself", "Uniqueness"};
        return lessons[guesses.size() % lessons.length];  // Rotate through lessons
    }

    private String getFeedback(int correctNumbers, int correctPositions, String lifeLesson) {
        String feedback = "";
        if (correctPositions == 4) {
            feedback = "You won! The secret combination is correct!";
        } else if (correctNumbers == 0 && correctPositions == 0) {
            feedback = "All incorrect.";
        } else {
            feedback = correctNumbers + " correct number(s) and " + correctPositions + " correct position(s).";
        }
        feedback += " Life Gem: " + lifeLesson;
        return feedback;
    }

    public int getAttemptsRemaining() {
        return attemptsRemaining;
    }

    public List<Guess> getGuesses() {
        return guesses;
    }

    public int[] getSecretCombination() {
        return secretCombination;
    }

    public boolean isGameOver() {
        return attemptsRemaining <= 0;
    }
}
