package me.xx.utility;

import java.util.Random;

import net.minecraft.util.MathHelper;

public class MathUtility {

    private static final Random random = new Random();

    public static double round(double in, int places) {
        places = (int) MathHelper.clamp_double(places, 0, Integer.MAX_VALUE);
        return Double.parseDouble(String.format("%." + places + "f", in));
    }

    public static double getRandom(double min, double max) {
        return MathHelper.clamp_double(min + random.nextDouble() * max, min, max);
    }

    public static int getRandom(int min, int max) {
        return MathHelper.clamp_int(min + random.nextInt() * max, min, max);
    }

    public static double square(double a) {
        return a * a;
    }

    public static double getPercent(double current, double max) {
        return (current / max);
    }

}