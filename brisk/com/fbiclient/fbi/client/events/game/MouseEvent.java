package com.fbiclient.fbi.client.events.game;

import me.xx.api.event.Event;

/**
 * @author Kyle
 * @since 2/1/2018
 **/
public class MouseEvent extends Event {

	private int button;

	public MouseEvent(final int button) {
		this.button = button;
	}

	public int getButton() {
		return this.button;
	}

	public void setButton(final int button) {
		this.button = button;
	}
}
