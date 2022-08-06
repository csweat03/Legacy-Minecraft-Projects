package club.shmoke.main.cheats.misc.jesus.impl;

import club.shmoke.main.cheats.misc.jesus.JesusHelper;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;

public class Spartan extends JesusHelper {

    private int ticks = 0;

    @Override
    public void onWater() {
        if (mc.thePlayer.isCollidedHorizontally && blockUtility.isInLiquid()) mc.thePlayer.motionY = 0.42;
        boolean invalid = (blockUtility.getBlockUnderPlayer(mc.thePlayer, 0) instanceof BlockAir && blockUtility.getBlockUnderPlayer(mc.thePlayer, 0.2) instanceof BlockLiquid) || mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown() || !(blockUtility.getBlockUnderPlayer(mc.thePlayer, 0.3) instanceof BlockLiquid) || !(blockUtility.getBlockUnderPlayer(mc.thePlayer, -0.6) instanceof BlockLiquid) || mc.thePlayer.isCollidedHorizontally;
        if (invalid) {
            ticks = -30;
            return;
        }

        ticks++;

        if (blockUtility.getBlockUnderPlayer(mc.thePlayer, -0.6) instanceof BlockLiquid) {
            if (mc.thePlayer.isMoving()) {
                mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 0.012);
            }
            mc.thePlayer.setSprinting(true);
            mc.thePlayer.motionY = (ticks > 0 && ticks % 40 == 0 ? 0.269 : -0.014);
        }
    }
}
