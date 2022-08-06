package club.shmoke.client.events.render;

import club.shmoke.api.event.Event;

public class ScoreboardEvent extends Event {
	
	String title = "";
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

}
