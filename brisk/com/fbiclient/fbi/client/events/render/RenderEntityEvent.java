package com.fbiclient.fbi.client.events.render;

import me.xx.api.event.Event;

/**
 * @author Kyle
 * @since 2/2/2018
 **/
public class RenderEntityEvent extends Event {
	private float ticks;

    public RenderEntityEvent(final float ticks) {
        this.ticks = ticks;
    }

    public float getPartialTicks() {
        return this.ticks;
    }
}
