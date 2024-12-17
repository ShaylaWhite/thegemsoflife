package com.gemsoflifegame;

import java.util.*;

public class ConsoleUI {
    private static GameController gameController;
    private static List<String> guessHistory = new ArrayList<>();
    private static List<String> lifeGems = new ArrayList<>();
    private static final String[] LIFE_GEMS = {
            "💎 Grit: \"Success is not final, failure is not fatal: It is the courage to continue that counts.\"",
            "✨ Self-Learning: \"The beautiful thing about learning is that no one can take it away from you.\"",
            "🔍 Problem-Solving: \"Don’t find fault, find a remedy.\"",
            "💪 Perseverance: \"Perseverance is not a long race; it is many short races one after the other.\"",
            "🔥 Passion: \"Passion is energy. Feel the power that comes from focusing on what excites you.\"",
            "💖 Self-Worth: \"You are enough just as you are.\"",
            "💫 Belief in Yourself: \"Believe you can and you’re halfway there.\"",
            "🌟 Uniqueness: \"Be yourself; everyone else is already taken.\""
    };

    public static void main(String[] args) {
        gameController = new GameController();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Gems of Life Game!");
        System.out.println("Try to guess the 4-number combination. You have 10 attempts.");

        // Game loop
        while (!gameController.isGameOver()) {
            System.out.println("Enter your guess (4 numbers between 0 and 7, separated by spaces):");
            String guessInput = scanner.nextLine();
            int[] playerGuess = Arrays.stream(guessInput.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (playerGuess.length != 4) {
                System.out.println("Invalid input! Please enter exactly 4 numbers.");
                continue;
            }

            String feedback = gameController.submitPlayerGuess(playerGuess);
            String lifeGem = getLifeGem(feedback);
            lifeGems.add(lifeGem);  // Add the life gem for this guess
            guessHistory.add("Guess: " + Arrays.toString(playerGuess) + " - Feedback: " + feedback + " | Life Gem: " + lifeGem);

            System.out.println(feedback + " Life Gem: " + lifeGem);

            // Display guess history and life gems
            System.out.println("Guess History:");
            for (String history : guessHistory) {
                System.out.println(history);
            }

            if (gameController.isGameOver()) {
                System.out.println("Game over. The secret combination was: " + Arrays.toString(gameController.getSecretCombination()));
                System.out.println("Here are the Life Gems you earned throughout the game:");
                for (String gem : lifeGems) {
                    System.out.println(gem);
                }
            }
        }
    }

    // Method to determine the Life Gem based on feedback and provide the gem emoji + quote
    private static String getLifeGem(String feedback) {
        if (feedback.contains("correct position")) {
            return LIFE_GEMS[0];  // Grit
        } else if (feedback.contains("correct number")) {
            return LIFE_GEMS[1];  // Self-Learning
        } else if (feedback.contains("all incorrect")) {
            return LIFE_GEMS[2];  // Problem-Solving
        } else {
            return LIFE_GEMS[3];  // Perseverance
        }
    }
}
