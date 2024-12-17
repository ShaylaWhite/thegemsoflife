package com.gemsoflifegame;
import java.io.*;
import java.net.*;
import java.util.*;

public class RandomNumberGenerator {
    public static int[] getRandomNumbers() throws IOException {
        // Call the Random.org API and parse the response
        String urlString = "https://www.random.org/integers/?num=4&min=0&max=7&col=1&base=10&format=plain";
        URL url = new URL(urlString);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        List<Integer> numbers = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            numbers.add(Integer.parseInt(line.trim()));
        }
        return numbers.stream().mapToInt(i -> i).toArray();
    }
}
