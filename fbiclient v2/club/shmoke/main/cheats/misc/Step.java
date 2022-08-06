package club.shmoke.main.cheats.misc;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.event.EventHandler;
import club.shmoke.api.utility.utilities.Timer;
import club.shmoke.main.events.UpdateEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;

/**
 * @author Christian
 */
public class Step extends Cheat {

    private Timer time = new Timer();

    public Step() {
        super("Step", 0, Category.MISC, "Makes you immune to retarded minquaf blocks.");
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {

        double extension = 0.05;
        float yaw = mc.thePlayer.rotationYaw;

        AxisAlignedBB bb = mc.thePlayer.boundingBox;

        double[] n = {MovementInput.moveForward * extension * Math.cos(Math.toRadians(yaw + 90.0f)), MovementInput.moveForward * extension * Math.sin(Math.toRadians(yaw + 90.0f))};
        double x = n[0] + MovementInput.moveStrafe * 0.1 * Math.sin(Math.toRadians(yaw + 90.0f));
        double z = n[1] - MovementInput.moveStrafe * 0.1 * Math.cos(Math.toRadians(yaw + 90.0f));

        boolean one = !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(x, 0.51, z)).isEmpty() && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(x, 1, z)).isEmpty();

        if (mc.thePlayer.inWater || mc.thePlayer.isOnLadder() || mc.gameSettings.keyBindJump.pressed)
            return;

        if (mc.thePlayer.motionY < 0.2 && !time.hasReached(400) && mc.gameSettings.keyBindForward.pressed) {
            //mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + (mc.thePlayer.isSprinting() ? 0.01 : 0.015));
            mc.thePlayer.motionY = -1;
        }

        if (one) {
            if (mc.thePlayer.onGround) mc.thePlayer.jump();
            time.reset();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.stepHeight = 0.5f;
    }

}
