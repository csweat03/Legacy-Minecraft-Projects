package club.shmoke.main.cheats.misc.jesus.impl;

import club.shmoke.main.cheats.misc.Jesus;
import club.shmoke.main.cheats.misc.jesus.JesusHelper;

public class NCP extends JesusHelper {

    @Override
    public void onWater() {
        if (blockUtility.isInLiquid()) {
            mc.thePlayer.setSprinting(true);
            mc.thePlayer.cameraYaw = 0.033999994f / 1.1f * 2;
            mc.thePlayer.motionY = mc.thePlayer.isCollidedHorizontally || mc.gameSettings.keyBindJump.isKeyDown() ? 0.42 : 0.005;
        }
    }
}
