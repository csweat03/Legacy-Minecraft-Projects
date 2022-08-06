package club.shmoke.main.cheats.move.speed.impl;

import club.shmoke.api.utility.utilities.Timer;
import club.shmoke.main.cheats.move.Speed;
import club.shmoke.main.cheats.move.speed.SpeedHelper;

public class HypixelPort extends SpeedHelper {

    private Timer timer = new Timer();

    @Override
    public void onUpdate() {
        if (mc.thePlayer.moveForward <= 0) return;

        mc.thePlayer.setSprinting(true);
        mc.thePlayer.setSpeed(mc.thePlayer.getSpeed());

        double z0000m = 0.025;
        long sleep = 600;

        mc.thePlayer.posY -= mc.thePlayer.motionY;
        mc.gameSettings.viewBobbing = false;

        if (mc.thePlayer.onGround) {
            mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + (mc.thePlayer.ticksExisted % 2 == 0 ? z0000m - 0.0005 : z0000m));
            mc.thePlayer.jump();
        }

        if (!timer.hasReached(sleep) && !mc.thePlayer.onGround)
            mc.thePlayer.motionY = -0.8;
        else if (timer.hasReached(sleep + 650)) timer.reset();
    }
}
