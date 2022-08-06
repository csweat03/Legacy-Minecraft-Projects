package club.shmoke.main.cheats.misc.jesus.impl;

import club.shmoke.main.cheats.misc.Jesus;
import club.shmoke.main.cheats.misc.jesus.JesusHelper;

public class BHop extends JesusHelper {

    @Override
    public void onWater() {
        if (blockUtility.isOnLiquid()) mc.thePlayer.motionY = 0.42;
    }
}
