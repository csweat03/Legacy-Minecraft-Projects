package club.shmoke.main.cheats.move.flight;

import club.shmoke.api.utility.Utility;
import club.shmoke.api.utility.utilities.VectorUtility;
import net.minecraft.util.Vec3;

public class Cubecraft extends Utility {

    public void onUpdate() {
        mc.timer.timerSpeed = 0.3f;
        if (mc.thePlayer.isMoving() && !mc.thePlayer.isCollidedHorizontally) {
            mc.thePlayer.setSpeed(1.2);
            Vec3 pos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            VectorUtility vec = new VectorUtility(pos, -mc.thePlayer.rotationYaw, 0.0F, 0.4);
            //mc.thePlayer.motionY += 0.01;
            if (mc.thePlayer.ticksExisted % 2 == 0) {
                mc.thePlayer.setPosition(vec.getEndVector().xCoord, vec.getEndVector().yCoord + Math.min(0.3 * Math.max(mc.thePlayer.fallDistance, 1), 0.6), vec.getEndVector().zCoord);
            } else {
                mc.thePlayer.setPosition(vec.getEndVector().xCoord, vec.getEndVector().yCoord - 0.005, vec.getEndVector().zCoord);
            }
        }
    }

}
