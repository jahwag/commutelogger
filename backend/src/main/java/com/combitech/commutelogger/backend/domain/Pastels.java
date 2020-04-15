package com.combitech.commutelogger.backend.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Pastels {

    private final List<String> taken = new LinkedList<>();

    private final Random random = new Random();

    public String next() {
        int sanity = 0;
        String color;

        do {
            color = generateRandomColor(167, 205, 189);
        } while (sanity++ < 10 && taken.contains(color));

        taken.add(color);
        return color;
    }

    public String generateRandomColor(int baseRed, int baseGreen, int baseBlue) {
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // mix the color
        red = (red + baseRed) / 2;
        green = (green + baseGreen) / 2;
        blue = (blue + baseBlue) / 2;

        return String.format("#%h%h%h", red, green, blue);
    }
}
