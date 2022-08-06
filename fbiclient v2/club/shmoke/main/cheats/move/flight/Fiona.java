package club.shmoke.main.cheats.move.flight;

import club.shmoke.api.utility.Utility;
import club.shmoke.api.utility.utilities.Timer;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Fiona extends Utility {

    private Timer timer = new Timer();

    public void onUpdate() {
//        if (blockUtility.getBlockUnderPlayer(mc.thePlayer, 2) instanceof BlockAir) {
//            mc.timer.timerSpeed = 0.9893452326f;
//            if (timer.hasReached((long) Math.min(60 * (Math.max(mc.thePlayer.fallDistance, 1)), 250))) {
//                mc.thePlayer.onGround = mc.gameSettings.keyBindSprint.pressed = mc.thePlayer.inWater = mc.thePlayer.isCollidedVertically = true;
//                mc.gameSettings.keyBindJump.pressed = false;
//
//                mc.thePlayer.motionY = 0;
//
//                //mc.thePlayer.setSpeed(playerUtility.getBaseMoveSpeed() - 0.06);
//
//                for (int i = 0; i < 3; i++)
//                    if (mc.thePlayer.ticksExisted % 3 == 0)
//                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1E-12, mc.thePlayer.posZ);
//                timer.reset();
//            } else {
//                mc.thePlayer.motionY = Math.max(-0.07 * Math.max(mc.thePlayer.fallDistance, 1), -0.44);
//            }
//        } else if (!mc.thePlayer.onGround && mc.thePlayer.isMoving() && mc.timer.timerSpeed == 0.9893452326f){
//            //mc.thePlayer.setSpeed(0.33);
//            mc.thePlayer.motionY = -0.2 * mc.thePlayer.fallDistance;
//        }

        if (blockUtility.getBlockUnderPlayer(mc.thePlayer, 3) instanceof BlockAir && mc.thePlayer.fallDistance <= 15) {
            if (mc.thePlayer.isMoving())
                mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? 1.25 * (mc.thePlayer.fallDistance / 2) : 1.3);
            if (mc.thePlayer.ticksExisted % 2 == 0) {
                mc.thePlayer.motionY = -3 * (mc.thePlayer.fallDistance > 2 ? mc.thePlayer.fallDistance : 1.8);
            } else if (blockUtility.getBlockUnderPlayer(mc.thePlayer, 1) instanceof BlockAir)
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.05, mc.thePlayer.posZ, false));
            mc.thePlayer.inWater = mc.thePlayer.isInWeb = true;
            mc.timer.timerSpeed = 0.3F;
        } else if (mc.timer.timerSpeed == 0.3F) {
            if (mc.thePlayer.isMoving())
                mc.thePlayer.setSpeed(0);
            if (mc.thePlayer.getSpeed() <= 0.1)
                mc.timer.timerSpeed = 1F;
        }
    }

}
