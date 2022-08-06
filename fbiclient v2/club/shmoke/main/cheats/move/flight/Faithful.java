package club.shmoke.main.cheats.move.flight;

import club.shmoke.api.utility.Utility;

public class Faithful extends Utility {

    public void onUpdate() {
        mc.thePlayer.setSprinting(true);
        mc.thePlayer.motionY = mc.gameSettings.keyBindJump.isKeyDown() ? 0.8 : mc.gameSettings.keyBindSneak.isKeyDown() ? -0.8 : 0;
        mc.thePlayer.posY = mc.thePlayer.posY + (mc.thePlayer.ticksExisted % 2 == 0 ? 0.000001 : -0.000001);
    }

}
