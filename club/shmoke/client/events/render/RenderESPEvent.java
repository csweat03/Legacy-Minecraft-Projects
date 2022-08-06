package club.shmoke.client.events.render;

import club.shmoke.api.event.Event;

public class RenderESPEvent extends Event {
	
	float partialTicks;
	
	public RenderESPEvent(float pTicks) {
		partialTicks = pTicks;
	}

}
