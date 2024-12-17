package com.gemsoflifegame.model;

public class Guess {
    private int[] playerGuess;
    private int correctNumbers;
    private int correctPositions;
    private String lifeLesson;

    public Guess(int[] playerGuess, int correctNumbers, int correctPositions, String lifeLesson) {
        this.playerGuess = playerGuess;
        this.correctNumbers = correctNumbers;
        this.correctPositions = correctPositions;
        this.lifeLesson = lifeLesson;
    }

    // Getters and toString() for displaying guess info
}
