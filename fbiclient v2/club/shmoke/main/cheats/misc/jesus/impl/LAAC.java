package club.shmoke.main.cheats.misc.jesus.impl;

import club.shmoke.main.cheats.misc.jesus.JesusHelper;
import net.minecraft.block.BlockLiquid;

public class LAAC extends JesusHelper {

    @Override
    public void onWater() {
        boolean shouldRun = blockUtility.getBlockUnderPlayer(mc.thePlayer, -0.3) instanceof BlockLiquid && !mc.thePlayer.isCollidedHorizontally;
        if (shouldRun) {
            mc.gameSettings.keyBindJump.setPressed(true);
            mc.thePlayer.setSprinting(true);
            if (mc.thePlayer.isMoving() && !(blockUtility.getBlockAbovePlayer(mc.thePlayer, -0.1) instanceof BlockLiquid))
                mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 0.0121);
            if (mc.thePlayer.motionY > 0)
                mc.thePlayer.motionY -= 0.08;
        } else if (mc.thePlayer.isCollidedHorizontally && blockUtility.isInLiquid()) {
            mc.gameSettings.keyBindJump.setPressed(false);
            mc.thePlayer.motionY = 0.1847890976;
        }
        if (mc.thePlayer.motionY == 0.1847890976)
            mc.gameSettings.keyBindJump.setPressed(false);
    }
}
