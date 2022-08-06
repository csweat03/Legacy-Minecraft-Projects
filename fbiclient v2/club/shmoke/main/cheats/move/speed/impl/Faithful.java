package club.shmoke.main.cheats.move.speed.impl;

import club.shmoke.main.cheats.move.speed.SpeedHelper;

public class Faithful extends SpeedHelper {

    @Override
    public void onUpdate() {
        mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + (mc.thePlayer.onGround ? 0.13f : 0));
        mc.thePlayer.motionY = mc.thePlayer.onGround ? 0.2f : mc.thePlayer.motionY;
        mc.timer.timerSpeed = 0.8f + (!mc.thePlayer.onGround ? 0.4f : 0);
    }
}
