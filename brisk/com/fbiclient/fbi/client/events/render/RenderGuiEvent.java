package com.fbiclient.fbi.client.events.render;

import me.xx.api.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author Kyle
 * @since 2/2/2018
 **/
public class RenderGuiEvent extends Event {

	public ScaledResolution getScaledRes() {
		return new ScaledResolution(Minecraft.getMinecraft());
	}

	public double getScreenWidth() {
		return getScaledRes().getScaledWidth_double();
	}

	public double getScreenHeight() {
		return getScaledRes().getScaledHeight_double();
	}

}
