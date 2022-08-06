package club.shmoke.main.cheats.move.speed.impl;

import club.shmoke.main.cheats.move.speed.SpeedHelper;

public class Cubecraft extends SpeedHelper {

    @Override
    public void onUpdate() {
        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.03, mc.thePlayer.posZ);
        if (mc.thePlayer.isMoving() && mc.thePlayer.onGround) {
            mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 5 == 0 ? (playerUtility.getBaseMoveSpeed() * 8)
                    : ((playerUtility.getBaseMoveSpeed() % 2 - 0.2) * 0.4));
        }
    }
}
