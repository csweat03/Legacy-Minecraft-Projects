package club.shmoke.main.cheats.move.speed.impl;

import club.shmoke.main.cheats.move.speed.SpeedHelper;

public class Fiona extends SpeedHelper {

    @Override
    public void onUpdate() {
        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.03, mc.thePlayer.posZ);
        if (mc.thePlayer.isMoving() && mc.thePlayer.onGround) {
            if (mc.thePlayer.ticksExisted % 5 == 0) {
                mc.thePlayer.setSpeed(playerUtility.getBaseMoveSpeed() * 10.15);
                mc.gameSettings.viewBobbing = false;
                mc.timer.timerSpeed = 0.21f;
            } else {
                mc.thePlayer.setSpeed(playerUtility.getBaseMoveSpeed() % 2 - 0.2);
                mc.timer.timerSpeed = 2.5f;
            }
        }
    }
}
