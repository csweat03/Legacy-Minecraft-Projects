package club.shmoke.main.cheats.move.speed.impl;

import club.shmoke.main.cheats.move.speed.SpeedHelper;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovementInput;

public class Frames extends SpeedHelper {

    @Override
    public void onUpdate() {
        if (!mc.thePlayer.isCollidedHorizontally) {
            double speed = 0.5;
            double x = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
            double z = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
            double forwardX = MovementInput.moveForward * speed * x;
            double offsetX = forwardX + MovementInput.moveStrafe * speed * z;
            double forwardZ = MovementInput.moveForward * speed * z;
            double offsetZ = forwardZ - MovementInput.moveStrafe * speed * x;
            if (mc.thePlayer.motionY <= -0.1) {
                double speedMultiplier = 10.0;
                if (blockUtility.getBlock(new BlockPos(mc.thePlayer.posX + offsetX * speedMultiplier, mc.thePlayer.posY + 1.0, mc.thePlayer.posZ + offsetZ * speedMultiplier)) instanceof BlockAir) {
                    if (blockUtility.getBlock(new BlockPos(mc.thePlayer.posX + offsetX * speedMultiplier, mc.thePlayer.posY + 2.0, mc.thePlayer.posZ + offsetZ * speedMultiplier)) instanceof BlockAir) {
                        mc.thePlayer.setPosition(mc.thePlayer.posX + offsetX * speedMultiplier, mc.thePlayer.posY,
                                mc.thePlayer.posZ + offsetZ * speedMultiplier);
                        mc.thePlayer.motionY = -0.001;
                    }
                }
            } else if (mc.thePlayer.onGround) {
                mc.thePlayer.motionY = 0.2;
            }
        }
    }
}
