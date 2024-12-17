package com.gemsoflifegame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int[] secretCombination;
    private int attemptsRemaining;
    private List<Guess> guesses;

    public Game() {
        this.secretCombination = generateSecretCombination();
        this.attemptsRemaining = 10;
        this.guesses = new ArrayList<>();
    }

    private int[] generateSecretCombination() {
        // Integrate with Random Number Generator API here
        // Mocked for now: [0, 1, 3, 5]
        return new int[] {0, 1, 3, 5};
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
        // Add logic to pick life lessons based on the number of attempts or guesses
        String[] lessons = {"Grit", "Self-Learning", "Problem-Solving", "Perseverance", "Passion", "Self-Worth", "Belief in Yourself", "Uniqueness"};
        Random rand = new Random();
        return lessons[rand.nextInt(lessons.length)];
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
}
