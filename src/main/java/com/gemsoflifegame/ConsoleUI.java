package com.gemsoflifegame;

import com.gemsoflifegame.model.Game;

import java.util.*;

public class ConsoleUI {
    private static Game game;

    public static void main(String[] args) {
        game = new Game();
        Scanner scanner = new Scanner(System.in);

        while (game.getAttemptsRemaining() > 0) {
            System.out.println("Guess the 4-number combination (0-7):");
            String guessInput = scanner.nextLine();
            int[] playerGuess = Arrays.stream(guessInput.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            String feedback = game.submitGuess(playerGuess);
            System.out.println(feedback);

            if (game.getAttemptsRemaining() == 0) {
                System.out.println("Game over. The secret combination was: " + Arrays.toString(game.secretCombination));
            }
        }
    }
}
