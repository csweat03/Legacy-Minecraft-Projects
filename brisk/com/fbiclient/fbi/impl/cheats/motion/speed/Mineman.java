package com.fbiclient.fbi.impl.cheats.motion.speed;

import com.fbiclient.fbi.client.framework.helper.IHelper;

public class Mineman implements IHelper {

	public void zoom() {
		mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.03, mc.thePlayer.posZ);
        if (mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
            mc.thePlayer.setSpeed((mc.thePlayer.ticksExisted % 3 == 0) ? (MOTION_HELPER.getBaseMoveSpeed() * 3.5)
                    : ((MOTION_HELPER.getBaseMoveSpeed() % 2) * 0.4));
        }
	}
	
}
