package club.shmoke.api.utility.utilities;

import net.minecraft.util.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * @author Christian
 */
public class MathUtility {

    private Random random = new Random();

    public double roundDouble(final double value, final int places)
    {
        if (places < 0)
        {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public float getNumberWithinRange(float min, float max)
    {
        for (int i = 0; i < 10000; i++)
        {
            float number = max * (float) Math.random();

            if (number > min)
            {
                return number;
            }
        }

        return 1;
    }

    public double getRandom(double min, double max) {
        return MathHelper.clamp_double(min + random.nextDouble() * max, min, max);
    }

    public int getRandom(int min, int max) {
        return MathHelper.clamp_int(min + random.nextInt() * max, min, max);
    }

    public double round(final double in, int places) {
        places = (int) clamp(places, 0.0, 2.147483647E9);
        return Double.parseDouble(String.format("%." + places + "f", in));
    }

    public double clamp(final double in, final double min, final double max) {
        return (in < min) ? min : ((in > max) ? max : in);
    }

    public double square(final double in) {
        return in * in;
    }

    public double sqrt(double value) {
        return StrictMath.sqrt(value);
    }

    public double wrapAngle(double angle) {
        angle %= 360.0;

        if (angle >= 180.0)
            angle -= 360.0;

        if (angle < -180.0)
            angle += 360.0;

        return angle;
    }

}
