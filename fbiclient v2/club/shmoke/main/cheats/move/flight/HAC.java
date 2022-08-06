package club.shmoke.main.cheats.move.flight;

import club.shmoke.api.utility.Utility;

public class HAC extends Utility {

    public void onUpdate() {
        if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.ticksExisted % 2 == 0) {
            mc.thePlayer.moveEntity(0, 0.2, 0);
            mc.thePlayer.setSpeed(0.15);
        } else if (mc.gameSettings.keyBindSneak.isKeyDown() && mc.thePlayer.ticksExisted % 2 == 0) {
            mc.thePlayer.moveEntity(0, -0.2, 0);
            mc.thePlayer.setSpeed(0.15);
        } else {
            mc.thePlayer.moveEntity(0, 0.01 * (mc.thePlayer.ticksExisted % 2 == 0 ? 1 : -1), 0);
            if (mc.thePlayer.isMoving())
                mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 0.001);
        }
        mc.thePlayer.motionY = 0;
        mc.thePlayer.setSprinting(true);
    }

}
