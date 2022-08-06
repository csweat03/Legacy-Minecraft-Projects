package club.shmoke.client.cheats.movement;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.client.util.math.DelayHelper;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;

public class Step extends Cheat {
    public final Property<Double> height = new Property<>(this, "Step Height", 1.5, 1.0, 10.0, 0.5);
    private final DelayHelper time = new DelayHelper();
    private final Property<Mode> modes = new Property<>(this, "Mode", Mode.PACKET);

    public Step() {
        super("Step", Type.MOVEMENT);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.stepHeight = 0.5F;
        mc.timer.timerSpeed = 1F;
    }

    @EventListener
    private void onUpdate(UpdatePlayerEvent event) {
        switch (modes.getValue()) {
            case VANILLA:
                height.setMax(10.0);
                vanilla();
                break;
            case PACKET:
                height.setMax(2.0);
                packet();
                break;
        }
        if (height.getValue() > height.getMax())
            height.setValue(height.getMax());
    }

    private void vanilla() {
        mc.thePlayer.stepHeight = height.getValue();
    }
    private void packet() {

        double extension = 0.05;

        float yaw = mc.thePlayer.rotationYaw;
        AxisAlignedBB bb = mc.thePlayer.boundingBox;

        double[] n = {MovementInput.moveForward * extension * Math.cos(Math.toRadians(yaw + 90.0f)), MovementInput.moveForward * extension * Math.sin(Math.toRadians(yaw + 90.0f))};
        double x = n[0] + MovementInput.moveStrafe * 0.1 * Math.sin(Math.toRadians(yaw + 90.0f));
        double z = n[1] - MovementInput.moveStrafe * 0.1 * Math.cos(Math.toRadians(yaw + 90.0f));

        boolean one = !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(x, 0.51, z)).isEmpty() && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(x, 1, z)).isEmpty();
        boolean onefive = !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(x, 1.4, z)).isEmpty() && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(x, 1.6, z)).isEmpty();
        boolean two = !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(x, 1.9, z)).isEmpty() && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(x, 2.1, z)).isEmpty();


        if (mc.thePlayer.inWater || mc.thePlayer.isOnLadder() || mc.gameSettings.keyBindJump.pressed)
            return;

        if (one && time.hasReached(100L)) {
            if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround) {
                C04(0.42);
                C04(0.753);
                mc.timer.timerSpeed = 0.2f;
                mc.thePlayer.stepHeight = 1F;
                time.reset();
            }
        } else if (onefive && time.hasReached(550L) && height.getValue() >= 1.5) {
            if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround) {
                C04(0.42);
                C04(0.75);
                C04(1);
                C04(1.16);
                C04(1.23);
                C04(1.2);
                mc.timer.timerSpeed = 0.2f;
                mc.thePlayer.stepHeight = 1.5F;
                time.reset();
            }
        } else if (two && time.hasReached(650L) && height.getValue() >= 2) {
            if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround) {
                C04(0.42);
                C04(0.78);
                C04(0.63);
                C04(0.51);
                C04(0.9);
                C04(1.21);
                C04(1.45);
                C04(1.43);
                mc.timer.timerSpeed = 0.2f;
                mc.thePlayer.stepHeight = 2F;
                time.reset();
            }
        } else {
            mc.timer.timerSpeed = 1;
        }

        if (!mc.thePlayer.isCollidedHorizontally) {
            mc.timer.timerSpeed = 1.0f;
            mc.thePlayer.stepHeight = 0.5f;
        }
    }

    private void C04(double y) {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                mc.thePlayer.posY + y, mc.thePlayer.posZ, mc.thePlayer.onGround));
    }


    private enum Mode {
        PACKET, VANILLA
    }
}
