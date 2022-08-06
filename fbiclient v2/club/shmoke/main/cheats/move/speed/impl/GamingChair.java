package club.shmoke.main.cheats.move.speed.impl;

import club.shmoke.main.cheats.move.speed.SpeedHelper;

public class GamingChair extends SpeedHelper {

    @Override
    public void onUpdate() {
        if (!mc.thePlayer.onGround || !mc.thePlayer.isMoving()) return;
        playerUtility.updatePosition(0, 0.03);
        mc.thePlayer.setSpeed((mc.thePlayer.ticksExisted % 3 == 0) ? playerUtility.getBaseMoveSpeed() * 5 : playerUtility.getBaseMoveSpeed() - 0.1);
    }
}
