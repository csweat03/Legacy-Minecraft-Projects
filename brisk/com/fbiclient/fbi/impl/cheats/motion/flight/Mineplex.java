package com.fbiclient.fbi.impl.cheats.motion.flight;

import com.fbiclient.fbi.client.framework.helper.IHelper;

public class Mineplex implements IHelper {

    public void dabOnSaidHaters() {
        if (mc.thePlayer.fallDistance > 0) {
            mc.timer.timerSpeed = 0.8f;
            mc.thePlayer.motionY += 0.05;
            mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 0.06);
        }
    }
}