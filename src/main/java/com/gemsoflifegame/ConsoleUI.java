package com.gemsoflifegame;

import com.gemsoflifegame.controller.GameController;

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
            "🌟 Uniqueness: \"Be yourself; everyone else is already taken.\"",
            "🌱 Growth: \"The only way to grow is to step out of your comfort zone.\"",
            "🌈 Hope: \"Hope is being able to see that there is light despite all of the darkness.\""
    };
    private static int guessCount = 0;
    private static final int MAX_GUESSES = 10;

    public static void main(String[] args) {
        gameController = new GameController();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Gems of Life Game!");
        System.out.println("Try to guess the 4-number combination. You have 10 attempts.");

        // Game loop with a maximum of 10 guesses
        while (!gameController.isGameOver() && guessCount < MAX_GUESSES) {
            System.out.println("Enter your guess (4 numbers between 0 and 7, separated by spaces):");
            String guessInput = scanner.nextLine();
            int[] playerGuess = Arrays.stream(guessInput.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (playerGuess.length != 4) {
                System.out.println("Invalid input! Please enter exactly 4 numbers.");
                continue;
            }

            // Increment the guess count
            guessCount++;

            String feedback = gameController.submitPlayerGuess(playerGuess);
            String lifeGem = getLifeGem(feedback);
            lifeGems.add(lifeGem);  // Add the life gem for this guess
            guessHistory.add("Guess: " + Arrays.toString(playerGuess) + " - Feedback: " + feedback + " | Life Gem: " + lifeGem);

            // Only print feedback and life gem here
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
                break;
            } else if (guessCount >= MAX_GUESSES) {
                System.out.println("You've reached the maximum number of guesses! Game over.");
                System.out.println("The secret combination was: " + Arrays.toString(gameController.getSecretCombination()));
                System.out.println("Here are the Life Gems you earned throughout the game:");
                for (String gem : lifeGems) {
                    System.out.println(gem);
                }
                break;
            }
        }
    }

    // Method to determine the Life Gem based on feedback and provide the gem emoji + quote
    private static String getLifeGem(String feedback) {
        if (feedback.contains("correct position") && feedback.contains("correct number")) {
            return LIFE_GEMS[0];  // Grit: When both number and position are correct
        } else if (feedback.contains("correct number")) {
            return LIFE_GEMS[1];  // Self-Learning: When a correct number is guessed but in the wrong position
        } else if (feedback.contains("correct position")) {
            return LIFE_GEMS[4];  // Passion: When the correct position is guessed but the number is wrong elsewhere
        } else if (feedback.contains("all incorrect")) {
            return LIFE_GEMS[2];  // Problem-Solving: When no numbers are correct
        } else if (feedback.contains("partial correct")) {
            return LIFE_GEMS[3];  // Perseverance: For a mix of correct and incorrect guesses
        } else if (feedback.contains("all correct")) {
            return LIFE_GEMS[5];  // Self-Worth: When all numbers are correct but not necessarily in the right place
        } else if (feedback.contains("more progress")) {
            return LIFE_GEMS[6];  // Belief in Yourself: For any other feedback that is still positive
        } else if (feedback.contains("partial success")) {
            return LIFE_GEMS[7];  // Uniqueness: For a mix of partial success
        } else if (feedback.contains("close to the goal")) {
            return LIFE_GEMS[8];  // Growth: For close attempts or minor mistakes
        } else if (feedback.contains("very close")) {
            return LIFE_GEMS[9];  // Hope: For attempts that are very close to the right answer
        } else {
            return LIFE_GEMS[6];  // Belief in Yourself: For any other feedback that is still positive
        }
    }
}
