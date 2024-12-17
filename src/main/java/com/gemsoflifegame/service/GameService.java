package com.gemsoflifegame.service;

import com.gemsoflifegame.model.Game;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GameService {

    public Game startNewGame() {
        int[] secretCombination = generateSecretCombination();
        return new Game(secretCombination);
    }

    private int[] generateSecretCombination() {
        // Call the Random API to get 4 random integers between 0 and 7
        try {
            URL url = new URL("https://www.random.org/integers/?num=4&min=0&max=7&col=1&base=10&format=plain");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            int[] combination = new int[4];
            for (int i = 0; i < 4; i++) {
                combination[i] = Integer.parseInt(in.readLine().trim());
            }
            in.close();
            return combination;

        } catch (Exception e) {
            e.printStackTrace();
            return new int[] {0, 1, 3, 5}; // Mocked data if API call fails
        }
    }
}
