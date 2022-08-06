package com.fbiclient.fbi.client.events.game;

import me.xx.api.event.Event;

/**
 * @author Kyle
 * @since 2/1/2018
 **/
public class KeypressEvent extends Event {

	private int key;

	public KeypressEvent(int key) {
		this.key = key;
	}

	public int getKey() {
		return this.key;
	}

}
