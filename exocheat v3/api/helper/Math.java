package club.shmoke.main.api.helper;

/**
 * @author Christian
 */
public class Math implements IBukkit {

    public static double round(final double in, int places) {
        places = (int) clamp(places, 0.0, 2.147483647E9);
        return Double.parseDouble(String.format("%." + places + "f", in));
    }

    public static double clamp(final double in, final double min, final double max) {
        return (in < min) ? min : ((in > max) ? max : in);
    }

    public static double square(final double in) {
        return in * in;
    }

    public static float sqrt(double value) {
        return (float) java.lang.Math.sqrt(value);
    }

    public static double wrapAngle(double angle) {
        angle %= 360.0;

        if (angle >= 180.0)
            angle -= 360.0;

        if (angle < -180.0)
            angle += 360.0;

        return angle;
    }
}
