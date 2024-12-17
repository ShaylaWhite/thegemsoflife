package com.gemsoflifegame;

import com.gemsoflifegame.controller.GameController;

import java.util.Arrays;
import java.util.Scanner;

public class ConsoleUI {
    private static GameController gameController;

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
            System.out.println(feedback);

            if (gameController.isGameOver()) {
                System.out.println("Game over. The secret combination was: " + Arrays.toString(gameController.getSecretCombination()));
            }
        }
    }
}
