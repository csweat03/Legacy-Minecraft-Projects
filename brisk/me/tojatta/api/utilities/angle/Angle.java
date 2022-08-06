package me.tojatta.api.utilities.angle;

import me.tojatta.api.utilities.vector.impl.Vector2;

/**
 * Created by Tojatta on 8/18/2016.
 */
public class Angle extends Vector2<Float> {

    public Angle(Float x, Float y) {
        super(x, y);
    }

    public Float getYaw() {
        return this.getX().floatValue();
    }

    public Float getPitch() {
        return this.getY().floatValue();
    }

    public void setYaw(Float yaw) {
        this.setX(yaw);
    }

    public void setPitch(Float pitch) {
        this.setY(pitch);
    }

}
