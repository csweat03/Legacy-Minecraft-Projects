package com.fbiclient.fbi.impl.cheats.motion.flight;

import org.lwjgl.input.Keyboard;

import com.fbiclient.fbi.client.framework.helper.IHelper;
import me.xx.utility.MathUtility;

public class Hypixel implements IHelper {

	public void evenMoreDab() {
		mc.thePlayer.motionY = 0;
		if (mc.thePlayer.isMoving()) {
			double random;
			float time;
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				time = mc.thePlayer.ticksExisted % 2 == 0 ? 2f : 1.2f;
				random = MathUtility.getRandom(0.03, 0.06);
			} else {
				time = mc.thePlayer.ticksExisted % 2 == 0 ? 1f : 1.3f;
				random = MathUtility.getRandom(0.01, 0.04);
			}
			double speed = mc.thePlayer.ticksExisted % 3 == 0 ? MOTION_HELPER.getBaseMoveSpeed() - random
					: 0.18f + random;
			double yaw = mc.thePlayer.rotationYawHead;
			yaw = Math.toRadians(yaw);
			double dX = -Math.sin(yaw) * random;
			double dZ = Math.cos(yaw) * random;
			if (!mc.thePlayer.isCollidedHorizontally)
				mc.thePlayer.setPosition(mc.thePlayer.posX + dX, mc.thePlayer.posY, mc.thePlayer.posZ + dZ);

			for (int i = 0; i < 3; i++) {
				// mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY,
				// mc.thePlayer.posZ);
				if (mc.thePlayer.ticksExisted % 3 == 0)
					mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1E-12, mc.thePlayer.posZ);
			}
			mc.thePlayer.setSpeed(speed);
			mc.timer.timerSpeed = time;
		} else {
			mc.thePlayer.motionX *= 0;
			mc.thePlayer.motionZ *= 0;
		}
	}
	
}
