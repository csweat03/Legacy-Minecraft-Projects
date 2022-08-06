package club.shmoke.main.cheats.move.flight;

import club.shmoke.api.utility.Utility;

public class CavePvP extends Utility {

    public void onUpdate() {
        if (mc.thePlayer.isMoving())
            mc.thePlayer.setSpeed(0.9);
        if (!mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown())
            mc.thePlayer.motionY = mc.thePlayer.ticksExisted % 10 == 0 ? 0.3 : -0.06;
        else
            playerUtility.updatePosition(0, mc.gameSettings.keyBindJump.isKeyDown() ? 2 : 0);
    }

}
