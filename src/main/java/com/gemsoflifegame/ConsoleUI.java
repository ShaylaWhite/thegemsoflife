package com.gemsoflifegame;

import com.gemsoflifegame.controller.GameController;
import com.gemsoflifegame.service.GameService;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class ConsoleUI {
    private static GameController gameController;
    private static List<String> guessHistory = new ArrayList<>();

    private static final String[] LIFE_GEMS = {
            "💎 Grit: \"Success is not final, failure is not fatal: It is the courage to continue that counts.\"",
            "✨ Self-Learning: \"The beautiful thing about learning is that no one can take it away from you.\"",
            "🔍 Problem-Solving: \"Don’t find fault, find a remedy.\"",
            "💪 Perseverance: \"It does not matter how slowly you go as long as you do not stop.\"",
            "🔥 Passion: \"Passion is energy. Feel the power that comes from focusing on what excites you.\"",
            "🌟 Self-Worth: \"You are enough, just as you are.\"",
            "🌈 Belief in Yourself: \"Believe you can and you're halfway there.\"",
            "🌱 Uniqueness: \"Don't be afraid to be unique, embrace it!\"",
            "🌻 Resilience: \"It's not how far you fall, but how high you bounce that counts.\"",
            "🧠 Growth: \"The only way to do great work is to love what you do.\""
    };

    private static final int MAX_GUESSES = 10;
    private static int guessCount;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Gems of Life Game!");
        System.out.println("In this game, you will guess a 4-digit number.");
        System.out.println("For each guess, you'll receive feedback on how many numbers are correct and how many are in the right position.");
        System.out.println("Every successful guess will unlock a life lesson to help guide your personal growth.");
        System.out.println("Let's begin!");

        RestTemplate restTemplate = new RestTemplate();
        GameService gameService = new GameService(restTemplate);
        gameController = new GameController(gameService);

        boolean playAgain;
        do {
            resetGame();
            playGame();
            playAgain = askPlayAgain();
        } while (playAgain);

        System.out.println("Thank you for playing!");
    }

    private static void playGame() {
        while (!gameController.isGameOver() && guessCount < MAX_GUESSES) {
            System.out.print("\nEnter your guess (4 numbers, space-separated): ");
            String input = scanner.nextLine();

            // Handle hint request
            if (input.equalsIgnoreCase("hint")) {
                System.out.println(gameController.provideHint());
                continue;
            }

            try {
                int[] guess = Arrays.stream(input.split(" "))
                        .mapToInt(Integer::parseInt).toArray();

                if (guess.length != 4) {
                    System.out.println("Please enter exactly 4 numbers separated by spaces.");
                    continue;
                }

                String result = gameController.checkGuess(guess);
                System.out.println("\nYour Guess: " + Arrays.toString(guess));
                System.out.println("Correct Numbers: " + gameController.getCorrectNumbers());
                System.out.println("Correct Positions: " + gameController.getCorrectPositions());

                // Display corresponding life lesson for the current guess
                if (guessCount < LIFE_GEMS.length) {
                    System.out.println("Life Lesson: " + LIFE_GEMS[guessCount]);
                }

                // Show guess history
                System.out.println("\nGuess History:");
                for (String guessHistoryEntry : gameController.getGuessHistory()) {
                    System.out.println(guessHistoryEntry);
                }

                // Show feedback and increment guess count
                guessCount++;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter numbers only.");
            }
        }

        if (gameController.isGameOver()) {
            System.out.println("Congratulations! You've won the game!");
        } else {
            System.out.println("Sorry, you've used all your guesses. The correct number was: " +
                    Arrays.toString(gameController.getTargetNumber()));
        }
    }

    private static boolean askPlayAgain() {
        System.out.print("\nWould you like to play again? (y/n): ");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("y");
    }

    private static void resetGame() {
        gameController.startNewGame();
        guessCount = 0;
    }
}
