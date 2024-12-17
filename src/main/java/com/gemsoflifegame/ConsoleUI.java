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
            "🔍 Problem-Solving: \"Don’t find fault, find a remedy.\""
    };

    private static final int MAX_GUESSES = 10;
    private static int guessCount;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Gems of Life Game!");

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
            System.out.print("Enter your guess (4 numbers, space-separated): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("hint")) {
                System.out.println(gameController.provideHint());
                continue;
            }

            try {
                int[] guess = Arrays.stream(input.split(" "))
                        .mapToInt(Integer::parseInt).toArray();

                if (guess.length != 4) {
                    System.out.println("Please enter exactly 4 numbers.");
                    continue;
                }

                String result = gameController.checkGuess(guess);
                System.out.println(result);
                guessHistory.add(Arrays.toString(guess) + " -> " + result);
                guessCount++;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter numbers only.");
            }
        }
        System.out.println(gameController.isGameOver() ? "You won!" : "Game over. Try again!");
    }

    private static void resetGame() {
        guessHistory.clear();
        guessCount = 0;
        gameController.startNewGame();
    }

    private static boolean askPlayAgain() {
        System.out.print("Play again? (yes/no): ");
        return scanner.nextLine().equalsIgnoreCase("yes");
    }
}
