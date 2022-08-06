package com.fbiclient.fbi.client.framework.helper.player;

import com.fbiclient.fbi.client.framework.helper.IHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class MotionHelper implements IHelper {

	public double getBaseMoveSpeed() {
		double baseSpeed = 0.2873;
		if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1 + 0.2 * (amplifier + 1);
		}
		return baseSpeed;
	}
	
	public static void offsetPosition(double speed) {
		double forward = mc.thePlayer.movementInput.moveForward;
		double strafe = mc.thePlayer.movementInput.moveStrafe;
		float yaw = mc.thePlayer.rotationYaw;
		if (forward == 0.0 && strafe == 0.0) {
			return;
		}
		if (forward != 0.0) {
			if (strafe > 0.0) {
				yaw += ((forward > 0.0) ? -45 : 45);
			} else if (strafe < 0.0) {
				yaw += ((forward > 0.0) ? 45 : -45);
			}
			strafe = 0.0;
			if (forward > 0.0) {
				forward = 1.0;
			} else if (forward < 0.0) {
				forward = -1.0;
			}
		}
		mc.thePlayer.setPositionAndUpdate(
				mc.thePlayer.posX + (forward * speed * Math.cos(Math.toRadians(yaw + 90.0f))
						+ strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f))),
				mc.thePlayer.posY, mc.thePlayer.posZ + (forward * speed * Math.sin(Math.toRadians(yaw + 90.0f))
						- strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f))));
	}

}
