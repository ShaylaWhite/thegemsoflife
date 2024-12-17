package com.gemsoflifegame;

import com.gemsoflifegame.model.Game;
import com.gemsoflifegame.service.GameService;

import java.util.Arrays;
import java.util.Scanner;

public class ConsoleUI {
    private static Game game;

    public static void main(String[] args) {
        GameService gameService = new GameService();  // GameService is now used to start a new game
        game = gameService.startNewGame();            // Start a new game
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Gems of Life Game!");
        System.out.println("Try to guess the 4-number combination. You have 10 attempts.");

        // Game loop
        while (game.getAttemptsRemaining() > 0) {
            System.out.println("Enter your guess (4 numbers between 0 and 7, separated by spaces):");
            String guessInput = scanner.nextLine();
            int[] playerGuess = Arrays.stream(guessInput.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (playerGuess.length != 4) {
                System.out.println("Invalid input! Please enter exactly 4 numbers.");
                continue;
            }

            String feedback = game.submitGuess(playerGuess);
            System.out.println(feedback);

            if (game.getAttemptsRemaining() == 0) {
                System.out.println("Game over. The secret combination was: " + Arrays.toString(game.getSecretCombination()));
            }
        }
    }
}
