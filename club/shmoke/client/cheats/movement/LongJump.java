package club.shmoke.client.cheats.movement;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.event.Event;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.events.update.TickEvent;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import net.minecraft.block.BlockAir;

public class LongJump extends Cheat {

    Property<Mode> mode = new Property<>(this, "Mode", Mode.GAURDIAN);
    Property<Integer> boost = new Property<>(this, "Boost", 3, 1, 10, 1);

    public LongJump() {
        super("LongJump", Type.MOVEMENT);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1F;
        mc.thePlayer.setSpeed(ENTITY_HELPER.getBaseMoveSpeed());
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent event) {
        if (event.type != Event.Type.PRE) return;
        if (mode.getValue() == Mode.CUBECRAFT) {
            ENTITY_HELPER.setSpeed(ENTITY_HELPER.getSpeed());

            if (mc.thePlayer.isMoving()) {
                float direction = mc.thePlayer.rotationYaw + ((mc.thePlayer.moveForward < 0.0f) ? 180 : 0)
                        + ((mc.thePlayer.moveStrafing > 0.0f) ? (-90.0f * ((mc.thePlayer.moveForward < 0.0f) ? -0.5f
                        : ((mc.thePlayer.moveForward > 0.0f) ? 0.5f : 1.0f))) : 0.0f)
                        - ((mc.thePlayer.moveStrafing < 0.0f) ? (-90.0f * ((mc.thePlayer.moveForward < 0.0f) ? -0.5f
                        : ((mc.thePlayer.moveForward > 0.0f) ? 0.5f : 1.0f))) : 0.0f);
                double xDir = Math.cos((direction + 90.0f) * Math.PI / 180.0);
                double zDir = Math.sin((direction + 90.0f) * Math.PI / 180.0);
                if (mc.thePlayer.onGround)
                    mc.thePlayer.jump2();
                if (mc.thePlayer.motionY == 0.33319999363422365 && mc.thePlayer.isMoving()) {
                    mc.thePlayer.motionX = xDir * 2;
                    mc.thePlayer.motionZ = zDir * 2;
                }
                if (!(BLOCK_HELPER.getBlockUnderPlayer(mc.thePlayer, 1.2) instanceof BlockAir) && mc.thePlayer.motionY < 0) {
                    mc.thePlayer.setSpeed(-0.5);
                    if (!(BLOCK_HELPER.getBlockUnderPlayer(mc.thePlayer, 0.8) instanceof BlockAir)) toggle();
                }
            }
        }
    }

    @EventListener
    public void onTick(TickEvent event) {
        if (mc.theWorld == null)
            return;

        if (mode.getValue() == Mode.GAURDIAN) {
            if (mc.thePlayer.isMoving()) {
                double[] dir = { Math.cos((getDirection() + 90) * Math.PI / 180), Math.sin((getDirection() + 90) * Math.PI / 180) };
                if (mc.thePlayer.onGround)
                    mc.thePlayer.jump();
                if (mc.thePlayer.motionY == 0.33319999363422365) {
                    mc.timer.timerSpeed = 2;
                    mc.thePlayer.motionX = dir[0] * boost.getValue();
                    mc.thePlayer.motionZ = dir[1] * boost.getValue();
                } else
                    mc.timer.timerSpeed = 1f;
            }
        }
    }

    private float getDirection() {
        float strafe = mc.thePlayer.moveStrafing, forward = mc.thePlayer.moveForward, yaw = mc.thePlayer.rotationYaw;
        return yaw + ((forward < 0) ? 180 : 0)
                + ((strafe > 0) ? (-90 * ((forward < 0) ? -0.5f
                : ((forward > 0) ? 0.5f : 1))) : 0)
                - ((strafe < 0) ? (-90f * ((forward < 0) ? -0.5f
                : ((forward > 0) ? 0.5f : 1))) : 0);
    }

    public enum Mode {
        GAURDIAN, CUBECRAFT
    }

}
