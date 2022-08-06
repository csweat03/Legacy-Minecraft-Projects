package club.shmoke.main.cheats.move.flight;

import club.shmoke.api.utility.Utility;
import club.shmoke.api.utility.utilities.Timer;
import club.shmoke.main.events.UpdateEvent;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;

public class MinemanClub extends Utility {

    public void onUpdate(UpdateEvent event) {
        mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? 5.0D : mc.thePlayer.movementInput.sneak ? -5.0D : 0.0D;
        mc.timer.timerSpeed = 0.3f;
        Timer timer = new Timer();
        if (timer.hasReached(400L)) {
            event.setY(event.getY() + 384);
            timer.reset();
        }
        mc.thePlayer.setSpeed(2.20);
//        if (!(blockUtility.getBlockUnderPlayer(mc.thePlayer, 1.5) instanceof BlockAir)) {
//            mc.thePlayer.moveEntity(0, 1, 0);
//            mc.timer.timerSpeed = 1;
//        } else {
//            if (mc.thePlayer.isMoving()) {
//                mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 15.5);
//            }
//            mc.thePlayer.motionY = 0.0D;
//            mc.thePlayer.moveStrafing = 0;
//            mc.thePlayer.onGround = true;
//            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - mc.thePlayer.fallDistance - 1.084131, mc.thePlayer.posZ, false));
//            mc.thePlayer.isInWeb = true;
//            mc.thePlayer.moveForward = 0;
//            mc.timer.timerSpeed = 0.25f;
//            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - mc.thePlayer.fallDistance, mc.thePlayer.posZ, false));
//            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - (mc.thePlayer.ticksExisted % 2 == 0 ? 0.01 : -0.01), mc.thePlayer.posZ);
//        }
    }
}
