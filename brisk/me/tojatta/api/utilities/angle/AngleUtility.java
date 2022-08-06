package me.tojatta.api.utilities.angle;

import me.tojatta.api.utilities.vector.impl.Vector3;

import java.util.Random;

/**
 * Created by Tojatta on 8/17/2016.
 */
public class AngleUtility {

    private float minYawSmoothing, maxYawSmoothing, minPitchSmoothing, maxPitchSmoothing;
    private final Random random;
    private Vector3<Float> delta;
    private boolean randomiztion;

    public AngleUtility(float minYawSmoothing, float maxYawSmoothing, float minPitchSmoothing, float maxPitchSmoothing, boolean randomiztion) {
        this.minYawSmoothing = minYawSmoothing;
        this.maxYawSmoothing = maxYawSmoothing;
        this.minPitchSmoothing = minPitchSmoothing;
        this.maxPitchSmoothing = maxPitchSmoothing;
        this.randomiztion = randomiztion;
        this.random = new Random();
        this.delta = new Vector3<>(0F, 0F, 0F);
    }

    public float randomFloat(float min, float max) {
        return min + (this.random.nextFloat() * (max - min));
    }

    private Angle constrainAngle(Angle vector) {

        vector.setYaw(vector.getYaw() % 360F);
        vector.setPitch(vector.getPitch() % 360F);

        while (vector.getYaw() <= -180) {
            vector.setYaw(vector.getYaw() + 360);
        }

        while (vector.getPitch() <= -180) {
            vector.setPitch(vector.getPitch() + 360);
        }

        while (vector.getYaw() > 180) {
            vector.setYaw(vector.getYaw() - 360);
        }

        while (vector.getPitch() > 180) {
            vector.setPitch(vector.getPitch() - 360);
        }

        return vector;
    }

    private float wrapDegrees(float value) {
        while (value <= -180) {
            value += 360;
        }
        while (value > 180) {
            value -= 360;
        }
        return value;
    }

    public Angle calculateAngle(Vector3<Double> dst, Vector3<Double> src) {
        Angle angles = new Angle(0F, 0F);
        this.delta.setX(dst.getX().floatValue() + (randomiztion ? randomFloat(-1F, 1F) : 0) - src.getX().floatValue());
        this.delta.setY((dst.getY().floatValue() + (randomiztion ? randomFloat(0, 2F) : 1.6F) /*Entity Height (Eye position)*/) - (src.getY().floatValue() + 1.6F /*Entity Height (Eye position)*/));
        this.delta.setZ(dst.getZ().floatValue() + (randomiztion ? randomFloat(-1F, 1F) : 0) - src.getZ().floatValue());

        double hypotenuse = Math.hypot(delta.getX().doubleValue(), delta.getZ().doubleValue());
        float yawAtan = ((float) Math.atan2(this.delta.getZ().floatValue(), this.delta.getX().floatValue()));
        float pitchAtan = ((float) Math.atan2(this.delta.getY().floatValue(), hypotenuse));
        float deg = ((float) (180 / Math.PI)); // 57.295779513082
        float yaw = ((yawAtan * deg) - 90F);
        float pitch = -(pitchAtan * deg);

        angles.setYaw(yaw);
        angles.setPitch(pitch);
        return angles;
    }

    private Angle smoothedAngle = new Angle(0F, 0F);

    /**
     * Used to smooth out your aim angles so you don't instantly snap to the next.
     *
     * @param dst Destination angle. (Angle needed for the target)
     * @param src Source angle. (Your current aim angles)
     * @return
     */
    public Angle smoothAngle(Angle dst, Angle src) {
        smoothedAngle.setYaw(src.getYaw() - dst.getYaw());
        smoothedAngle.setPitch(src.getPitch() - dst.getPitch());
        smoothedAngle = constrainAngle(smoothedAngle);
        smoothedAngle.setYaw(src.getYaw() - smoothedAngle.getYaw() / 100 * randomFloat(minYawSmoothing, maxYawSmoothing));
        smoothedAngle.setPitch(src.getPitch() - smoothedAngle.getPitch() / 100 * randomFloat(minPitchSmoothing, maxPitchSmoothing));
        return smoothedAngle;
    }

}
