package me.xx.utility.render;

import net.minecraft.client.Minecraft;

/**
 * @author Kyle
 */
public class Interpolate {

	/**
	 * @return An interpolation between two positions
	 */
	public static double interpolate(double newPos, double oldPos) {
		return oldPos + (newPos - oldPos) * Minecraft.getMinecraft().timer.renderPartialTicks;
	}

}
