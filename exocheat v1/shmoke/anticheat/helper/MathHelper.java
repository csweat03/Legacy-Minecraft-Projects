package club.shmoke.anticheat.helper;

public class MathHelper implements IBukkit {
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

    public double[] getRotationsNeeded(double x, double y, double z) {
        double xSize = x - getPlayer().getLocation().getX();
        double ySize = y - getPlayer().getLocation().getY();
        double zSize = z - getPlayer().getLocation().getZ();
        double theta = (double) sqrt(xSize * xSize + zSize * zSize);
        double yaw = (Math.atan2(zSize, xSize) * 180.0D / Math.PI) - 90.0F;
        double pitch = -(Math.atan2(ySize, theta) * 180.0D / Math.PI);
        return new double[]{
                (getYaw() + wrapAngle(yaw - getYaw())) % 360,
                (getPitch() + wrapAngle(pitch - getPitch())) % 360,};
    }

    public float sqrt(double value) {
        return (float) Math.sqrt(value);
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
