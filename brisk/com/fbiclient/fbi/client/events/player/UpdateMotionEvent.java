package com.fbiclient.fbi.client.events.player;

import me.xx.api.event.Event;

/**
 * @author Kyle
 * @since 2/1/2018
 **/

public class UpdateMotionEvent extends Event {

	private double y;
	private float yaw, pitch;
	private boolean onGround, forceFullUpdate;

	public UpdateMotionEvent(double y, float yaw, float pitch, boolean onGround) {
		this.y = y;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
		this.forceFullUpdate = false;
		this.setType(Event.Type.PRE);
	}

	public UpdateMotionEvent() {
		this.setType(Type.POST);
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public boolean isForceFullUpdate() {
		return this.forceFullUpdate;
	}

	public void forceFullUpdate(boolean forceFullUpdate) {
		this.forceFullUpdate = forceFullUpdate;
	}
}
