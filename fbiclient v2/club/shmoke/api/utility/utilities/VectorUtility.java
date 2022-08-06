package club.shmoke.api.utility.utilities;

import net.minecraft.util.Vec3;

import java.util.ArrayList;

public class VectorUtility {

    private Vec3 vec1, vec2, vec3;
    private double offsetX, offsetY, offsetZ;

    public VectorUtility(Vec3 start, Vec3 end) {
        this.vec1 = start;
        this.vec3 = end;
        init(start, end);
    }

    public VectorUtility(Vec3 vector, float yaw, float pitch, double length) {
        vec1 = vector;
        double calculatedX = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        double calculatedY = Math.sin(Math.toRadians(pitch));
        double calculatedZ = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        double x = calculatedX * length + vector.xCoord;
        double y = calculatedY * length + vector.yCoord;
        double z = calculatedZ * length + vector.zCoord;
        vec3 = new Vec3(x, y, z);
        init(vec1, vec3);

    }

    private void init(Vec3 start, Vec3 end) {
        this.vec2 = calculate(start, end);
        this.offsetX = end.xCoord - start.xCoord;
        this.offsetY = end.yCoord - start.yCoord;
        this.offsetZ = end.zCoord - start.zCoord;
    }

    private Vec3 calculate(Vec3 v1, Vec3 v2) {
        double x = (v1.xCoord + v1.yCoord) / 2;
        double y = (v1.yCoord + v2.yCoord) / 2;
        double z = (v1.zCoord + v2.zCoord) / 2;

        return new Vec3(x, y, z);
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getOffsetZ() {
        return offsetZ;
    }


    public Vec3 getStartVector() {
        return vec1;
    }

    public double getLength(Vec3 vector1, Vec3 vector2) {
        return vector1.distanceTo(vector2);
    }

    public ArrayList<Vec3> getIntervalTimer(final double interval) {
        final int intervaL = (int) (this.getStartVector().distanceTo(this.getEndVector()) / interval) + 1;
        return this.calculateInterval(intervaL);
    }

    private ArrayList<Vec3> calculateInterval(int interval) {
        interval--;
        final ArrayList<Vec3> points = new ArrayList<>();
        final double xOff = this.getOffsetX() / interval;
        final double yOff = this.getOffsetY() / interval;
        final double zOff = this.getOffsetZ() / interval;
        for (int i = 0; i <= interval; ++i) {
            final double xOffset = xOff * i;
            final double yOffset = yOff * i;
            final double zOffset = zOff * i;
            points.add(new Vec3(getStartVector().xCoord + xOffset, getStartVector().yCoord + yOffset, getStartVector().zCoord + zOffset));
        }
        return points;
    }

    public Vec3 getEndVector() {
        return vec3;
    }
}
