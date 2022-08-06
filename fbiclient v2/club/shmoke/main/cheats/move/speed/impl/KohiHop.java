package club.shmoke.main.cheats.move.speed.impl;

import club.shmoke.main.cheats.move.speed.SpeedHelper;

public class KohiHop extends SpeedHelper {

    @Override
    public void onUpdate() {
        mc.thePlayer.setSpeed(mc.thePlayer.getSpeed());
        mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 0.06);
        if (mc.thePlayer.onGround) {
            mc.thePlayer.motionY = 0.42;
        } else if (mc.thePlayer.fallDistance > 0.2) {
            mc.thePlayer.setSpeed(0.55);
        }
    }
}
