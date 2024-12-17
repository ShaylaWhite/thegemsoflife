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

    // Color Codes
    private static final String RESET = "\033[0m";
    private static final String RED = "\033[31m";
    private static final String GREEN = "\033[32m";
    private static final String YELLOW = "\033[33m";
    private static final String BLUE = "\033[34m";
    private static final String MAGENTA = "\033[35m";
    private static final String CYAN = "\033[36m";
    private static final String BOLD = "\033[1m";

    public static void main(String[] args) {
        gameController = new GameController();
        Scanner scanner = new Scanner(System.in);

        System.out.println(BOLD + CYAN + "Welcome to the Gems of Life Game!" + RESET);
        System.out.println("Try to guess the 4-number combination. You have 10 attempts.");

        // Game loop with a maximum of 10 guesses
        while (!gameController.isGameOver() && guessCount < MAX_GUESSES) {
            System.out.println(MAGENTA + "Enter your guess (4 numbers between 0 and 7, separated by spaces):" + RESET);
            String guessInput = scanner.nextLine();
            int[] playerGuess = Arrays.stream(guessInput.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (playerGuess.length != 4) {
                System.out.println(RED + "Invalid input! Please enter exactly 4 numbers." + RESET);
                continue;
            }

            // Increment the guess count
            guessCount++;

            // Get feedback and determine the corresponding Life Gem
            String feedback = gameController.submitPlayerGuess(playerGuess);
            String lifeGem = getLifeGem(feedback);  // Get Life Gem based on feedback

            // Only add the guess (without feedback and life gem) to the guess history
            guessHistory.add("Guess: " + Arrays.toString(playerGuess));

            // Print feedback and Life Gem
            System.out.println("Feedback: " + feedback);
            System.out.println(GREEN + "💎 Life Gem: " + getGemName(lifeGem) + RESET);
            System.out.println("Life Lesson on " + getGemName(lifeGem) + ": " + getGemLesson(lifeGem));

            // Display guess history
            System.out.println("Guess History:");
            for (String history : guessHistory) {
                System.out.println(BLUE + history + RESET);
            }

            // Show how many guesses are left
            System.out.println(YELLOW + "Guesses Left: " + (MAX_GUESSES - guessCount) + RESET);

            if (gameController.isGameOver()) {
                System.out.println(RED + "Game over. The secret combination was: " + Arrays.toString(gameController.getSecretCombination()) + RESET);
                System.out.println("Here are the Life Gems you earned throughout the game:");
                for (String gem : lifeGems) {
                    System.out.println(CYAN + gem + RESET);
                }
                break;
            } else if (guessCount >= MAX_GUESSES) {
                System.out.println(RED + "You've reached the maximum number of guesses! Game over." + RESET);
                System.out.println("The secret combination was: " + Arrays.toString(gameController.getSecretCombination()));
                System.out.println("Here are the Life Gems you earned throughout the game:");
                for (String gem : lifeGems) {
                    System.out.println(CYAN + gem + RESET);
                }
                break;
            }
        }
    }

    // Method to determine the Life Gem based on feedback and provide the gem emoji + quote
    private static String getLifeGem(String feedback) {
        // Debugging feedback
        System.out.println("Feedback: " + feedback);

        // Match specific feedback and return corresponding Life Gem
        if (feedback.contains("correct position") && feedback.contains("correct number")) {
            return LIFE_GEMS[0];  // Grit: Both number and position are correct
        } else if (feedback.contains("correct number") && !feedback.contains("correct position")) {
            return LIFE_GEMS[1];  // Self-Learning: Correct number, wrong position
        } else if (feedback.contains("correct position") && !feedback.contains("correct number")) {
            return LIFE_GEMS[4];  // Passion: Correct position, wrong number
        } else if (feedback.contains("all incorrect")) {
            return LIFE_GEMS[2];  // Problem-Solving: All numbers incorrect
        } else if (feedback.contains("partial correct")) {
            return LIFE_GEMS[3];  // Perseverance: Mix of correct and incorrect guesses
        } else if (feedback.contains("all correct")) {
            return LIFE_GEMS[5];  // Self-Worth: All numbers correct (but not necessarily in the right place)
        } else if (feedback.contains("more progress")) {
            return LIFE_GEMS[6];  // Belief in Yourself: Positive feedback indicating progress
        } else if (feedback.contains("partial success")) {
            return LIFE_GEMS[7];  // Uniqueness: Partial success
        } else if (feedback.contains("close to the goal")) {
            return LIFE_GEMS[8];  // Growth: Close attempts, minor mistakes
        } else if (feedback.contains("very close")) {
            return LIFE_GEMS[9];  // Hope: Very close to the right answer
        } else {
            return LIFE_GEMS[6];  // Default: Belief in Yourself if nothing else matches
        }
    }


    // Extract the name from the life gem (e.g., "Grit")
    private static String getGemName(String lifeGem) {
        return lifeGem.split(":")[0].trim();
    }

    // Extract the life lesson/quote from the life gem (e.g., the motivational quote)
    private static String getGemLesson(String lifeGem) {
        return lifeGem.split(":")[1].trim();
    }
}
