package com.fbiclient.fbi.client.events.player;

import me.xx.api.event.Event;

/**
 * @author Kyle
 * @since 2/1/2018
 **/
public class ChatEvent extends Event {

	private String message;

	public ChatEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
