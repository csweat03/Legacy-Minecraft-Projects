package com.fbiclient.fbi.client.events.render;

import me.xx.api.event.Event;
import net.minecraft.entity.Entity;
/**
 * @author Kyle
 * @since 2/2/2018
 **/
public class RenderNametagEvent extends Event {
	public Entity entityIn;
    public double x,y,z;
    public String str;

    public RenderNametagEvent(Entity entityIn, double x, double y, double z, String str) {
        this.entityIn = entityIn;
        this.x = x;
        this.y = y;
        this.z = z;
        this.str = str;
    }

    public Entity getEntityIn() {
        return entityIn;
    }

    public void setEntityIn(Entity entityIn) {
        this.entityIn = entityIn;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
