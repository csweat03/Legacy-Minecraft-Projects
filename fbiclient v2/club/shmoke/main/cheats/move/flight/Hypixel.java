package club.shmoke.main.cheats.move.flight;

import club.shmoke.api.utility.Utility;

public class Hypixel extends Utility {

    public void onUpdate() {
        mc.thePlayer.cameraYaw = 0.125f;
        mc.thePlayer.onGround = mc.gameSettings.keyBindSprint.pressed = mc.thePlayer.inWater = mc.thePlayer.isCollidedVertically = true;
        mc.thePlayer.motionY = 0;

        for (int i = 0; i < 3; i++)
            if (mc.thePlayer.ticksExisted % 3 == 0)
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1E-12, mc.thePlayer.posZ);
    }

}
