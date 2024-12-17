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

    public int[] getPlayerGuess() {
        return playerGuess;
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

    @Override
    public String toString() {
        StringBuilder guessString = new StringBuilder();
        guessString.append("Guess: ");
        for (int num : playerGuess) {
            guessString.append(num).append(" ");
        }
        guessString.append("| Correct Numbers: ").append(correctNumbers)
                .append(" | Correct Positions: ").append(correctPositions)
                .append(" | Life Lesson: ").append(lifeLesson);
        return guessString.toString();
    }
}
