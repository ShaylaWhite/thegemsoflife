package com.gemsoflifegame;

import com.gemsoflifegame.controller.GameController;
import com.gemsoflifegame.model.Guess;
import com.gemsoflifegame.service.GameService;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;
import java.util.Arrays;

public class ConsoleUI {
    private static GameController gameController;
    private static int guessCount;
    private static final int MAX_GUESSES = 10;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("       Welcome to the Life Lessons Guessing Game!");
        System.out.println("===============================================");
        System.out.println("The secret combination consists of four different numbers, each between 0 and 7.");
        System.out.println("You have " + MAX_GUESSES + " attempts to guess the combination correctly.");
        System.out.println("===============================================");

        // Create an instance of GameService and pass it to the GameController
        GameService gameService = new GameService(new RestTemplate());
        gameController = new GameController(gameService);

        boolean playAgain;
        do {
            resetGame();
            playGame();
            playAgain = askPlayAgain();
        } while (playAgain);

        System.out.println("===============================================");
        System.out.println("        Thank you for playing! See you soon.");
        System.out.println("===============================================");
    }

    private static void playGame() {
        while (!gameController.isGameOver() && guessCount < MAX_GUESSES) {
            System.out.print("\nEnter your guess (4 numbers, space-separated): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("hint")) {
                // Show hint if user types "hint"
                String hint = getHint();
                System.out.println("\nHint: " + hint);
                continue;
            }

            try {
                int[] guess = Arrays.stream(input.split(" ")).mapToInt(Integer::parseInt).toArray();

                if (guess.length != 4) {
                    System.out.println("\n**Error**: Please enter exactly 4 numbers separated by spaces.");
                    continue;
                }

                String result = gameController.submitGuess(guess);
                System.out.println("\n" + result);

                // Display gems and life lesson with explanation
                displayGemsAndLifeLesson(guess);

                // Display guess history
                System.out.println("\n============== Guess History ==============");
                for (Guess history : gameController.getGuessHistory()) {
                    System.out.println("\nGuess: " + Arrays.toString(history.getPlayerGuess()) +
                            " | Correct Numbers: " + history.getCorrectNumbers() +
                            " | Correct Positions: " + history.getCorrectPositions());
                    System.out.println("Life Lesson: " + history.getLifeLesson());
                }

                guessCount++;

                // Display guesses left
                System.out.println("\nYou have " + (MAX_GUESSES - guessCount) + " guesses left.");
                System.out.println("===============================================");

                // Check if the guess is correct
                if (gameController.isCorrectGuess(guess)) {
                    System.out.println("\n🎉 Congratulations! You've guessed the correct combination! 🎉");
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("\n**Error**: Invalid input! Enter numbers only.");
            }
        }

        if (guessCount >= MAX_GUESSES && !gameController.isCorrectGuess(new int[0])) {
            System.out.println("\n**Game Over**: You've used all your guesses. The correct combination was: " +
                    Arrays.toString(gameController.getCurrentGame().getSecretCombination()));
        }
    }

    private static void displayGemsAndLifeLesson(int[] guess) {
        // Retrieve correct numbers and positions for the current guess
        int correctNumbers = gameController.getCurrentGame().getCorrectNumbers(guess);
        int correctPositions = gameController.getCurrentGame().getCorrectPositions(guess);

        // Generate gem explanation based on the current guess
        StringBuilder gemExplanation = new StringBuilder("You earned gems for your guess: ");

        // Award gems for correct numbers
        if (correctNumbers > 0) {
            gemExplanation.append(correctNumbers).append(" gem(s) for ").append(correctNumbers).append(" correct number(s). ");
        }

        // Award gems for correct positions
        if (correctPositions > 0) {
            gemExplanation.append(correctPositions).append(" gem(s) for ").append(correctPositions).append(" correct position(s). ");
        }

        // If no correct numbers or positions, mention no gems earned
        if (correctNumbers == 0 && correctPositions == 0) {
            gemExplanation.append("No gems earned this time. ");
        }

        // Display gem explanation
        System.out.println("\n" + gemExplanation.toString().trim());
        System.out.println("===============================================");

        // Provide a life lesson based on the correct numbers and positions
        System.out.println("Life Lesson: " + gameController.getCurrentGame().getLifeLesson(correctNumbers, correctPositions));
        System.out.println("===============================================");
    }

    private static String getHint() {
        // Provide a hint by revealing one of the secret combination numbers
        int[] secretCombination = gameController.getCurrentGame().getSecretCombination();
        return "One of the numbers is: " + secretCombination[(int)(Math.random() * secretCombination.length)];
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
