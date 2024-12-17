import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    // List of life lessons to give after each guess
    private static final List<String> LIFE_GEMS = List.of(
            "Grit", "Self-learning", "Problem-solving", "Perseverance",
            "Passion", "Self-worth", "Belief in yourself", "Uniqueness"
    );

    // The random pattern for the game (4 digits)
    private List<Integer> secretPattern;
    private int remainingAttempts = 10; // Number of guesses

    // Constructor to initialize the game
    public Game() {
        secretPattern = generateSecretPattern();
    }

    // Simulate Random Number Generator (replace this with API later)
    private List<Integer> generateSecretPattern() {
        List<Integer> pattern = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            pattern.add(random.nextInt(8)); // Random number between 0 and 7
        }
        return pattern;
    }

    // Function to check the player's guess
    public String checkGuess(List<Integer> guess) {
        int correctNumbers = 0;
        int correctPositions = 0;

        // Check for correct numbers and correct positions
        for (int i = 0; i < 4; i++) {
            if (guess.get(i).equals(secretPattern.get(i))) {
                correctPositions++;
            } else if (secretPattern.contains(guess.get(i))) {
                correctNumbers++;
            }
        }

        if (correctPositions == 4) {
            return "You won! All numbers and positions are correct!";
        }

        // If not all correct, give feedback
        return correctNumbers + " correct number(s) and " + correctPositions + " correct position(s).";
    }

    // Display a random life gem (motivational lesson)
    public String getLifeGem() {
        Random random = new Random();
        return LIFE_GEMS.get(random.nextInt(LIFE_GEMS.size()));
    }

    // Start the game
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Gems of Life Game! Try to guess the 4-number combination.");
        System.out.println("You have " + remainingAttempts + " attempts.");

        while (remainingAttempts > 0) {
            System.out.println("Enter your guess (4 numbers from 0 to 7): ");
            String guessInput = scanner.nextLine();

            // Parse the input into integers
            List<Integer> guess = new ArrayList<>();
            try {
                for (String num : guessInput.split(" ")) {
                    guess.add(Integer.parseInt(num));
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter 4 numbers separated by spaces.");
                continue;
            }

            // Check the guess
            String feedback = checkGuess(guess);
            System.out.println(feedback);

            // Show the corresponding life gem
            System.out.println("Life Gem: " + getLifeGem());

            remainingAttempts--;
            System.out.println("Attempts left: " + remainingAttempts);

            // If the player wins, break the loop
            if (feedback.contains("You won!")) {
                break;
            }
        }

        if (remainingAttempts == 0) {
            System.out.println("Game over! The correct pattern was: " + secretPattern);
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
