package com.fbiclient.fbi.client.events.player;

import me.xx.api.event.Event;

/**
 * @author Kyle
 * @since 2/1/2018
 **/
public class MoveEvent extends Event {

	public double x;
	public double y;
	public double z;

	public MoveEvent(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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

}
