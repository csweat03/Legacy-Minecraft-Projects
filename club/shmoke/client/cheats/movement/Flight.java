package club.shmoke.client.cheats.movement;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.events.entity.JumpEvent;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class Flight extends Cheat implements IHelper {

    public Property<Mode> mode = new Property<>(this, "Mode", Mode.VANILLA);
    public Property<Integer> boost = new Property<>(this, "Boost", 2, 1, 3, 1);

    public Flight() {
        super("Flight", Type.MOVEMENT);
        this.description = "Fly like a bird!";
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent event) {
        mc.thePlayer.cameraYaw = 0.057999994f / 1.1f * 2;
        boost.setMax(mode.getValue() == Mode.VANILLA || mode.getValue() == Mode.OLDGUARDIAN ? 10 : mode.getValue() == Mode.HYPIXEL ? 2 : 0);
        if (boost.getValue() > boost.getMax()) boost.setValue(boost.getMax());
        switch (event.type) {
            case PRE:
                switch (mode.getValue()) {
                    case VANILLA:
                        mc.thePlayer.capabilities.isFlying = true;
                        mc.thePlayer.capabilities.setFlySpeed(boost.getValue() * 0.1F);
                        break;
                    case HAC:
                        double up = 0.5;
                        if (mc.gameSettings.keyBindJump.pressed || mc.gameSettings.keyBindSneak.pressed) {
                            mc.thePlayer.motionY = -1;
                            if (mc.thePlayer.ticksExisted % 4 == 0)
                                mc.thePlayer.moveEntity(0, mc.gameSettings.keyBindJump.pressed ? up : -up, 0);
                        } else if (mc.thePlayer.ticksExisted % 10 == 0) {
                            mc.thePlayer.moveEntity(mc.thePlayer.motionX, 0.3, mc.thePlayer.motionZ);
                            if (mc.thePlayer.isMoving())
                                mc.thePlayer.setSpeed(0.19);
                        }
                        mc.thePlayer.motionY = -0.03;
                        break;
                    case FIONA:
                        if (BLOCK_HELPER.getBlockUnderPlayer(mc.thePlayer, 3) instanceof BlockAir) {
                            if (mc.thePlayer.isMoving())
                                mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? 1.25 * (mc.thePlayer.fallDistance / 1.75) : 1.3);
                            if (mc.thePlayer.ticksExisted % 2 == 0) {
                                mc.thePlayer.motionY = -3 * (mc.thePlayer.fallDistance > 2 ? mc.thePlayer.fallDistance : 1.8);
                            } else if (BLOCK_HELPER.getBlockUnderPlayer(mc.thePlayer, 1) instanceof BlockAir)
                                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.31, mc.thePlayer.posZ, false));
                            mc.thePlayer.inWater = mc.thePlayer.isInWeb = true;
                            mc.timer.timerSpeed = 0.4F;
                        } else if (mc.thePlayer.getSpeed() > 0.3) {
                            if (mc.thePlayer.isMoving())
                                mc.thePlayer.setSpeed(0);
                            mc.timer.timerSpeed = 1F;
                        }
                        break;
                    case HYPIXEL:
                        mc.thePlayer.onGround = mc.gameSettings.keyBindSprint.pressed = true;
                        mc.thePlayer.motionY = 0;
                        if (boost.getValue() == 2)
                            mc.thePlayer.inWater = mc.thePlayer.isCollidedVertically = event.onGround = true;
                        mc.thePlayer.bypassHypixel();
                        if (mc.thePlayer.isMoving() && boost.getValue() == 2)
                            boost();
                        break;
                    case OLDGUARDIAN:
                        mc.thePlayer.motionY = mc.gameSettings.keyBindJump.pressed ? 2 : mc.gameSettings.keyBindSneak.pressed ? -2 : mc.thePlayer.motionY < -0.14 ? 0.05 : mc.thePlayer.motionY;
                        if (mc.thePlayer.ticksExisted % 30 == 0 && mc.thePlayer.isMoving())
                            mc.thePlayer.setSpeed((boost.getValue() * 0.6) + 0.5);
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    private void boost() {
        float time = 1;
        if (Keyboard.getEventKey() == Keyboard.KEY_LCONTROL) {
            time = mc.thePlayer.ticksExisted % 2 == 0 ? 4 : 2;
        } else {
            time = mc.thePlayer.ticksExisted % 2 == 0 ? 1.9f : 1;
        }
        mc.timer.timerSpeed = time;
    }

    @EventListener
    public void onJump(JumpEvent e) {
        e.cancel();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
        if (mc.thePlayer.isMoving())
            mc.thePlayer.setSpeed(ENTITY_HELPER.getBaseMoveSpeed());
        mc.thePlayer.capabilities.isFlying = false;
        mc.thePlayer.capabilities.setFlySpeed(0.1F);
        super.onDisable();
    }

    public enum Mode {
        VANILLA, HYPIXEL, FIONA, HAC, OLDGUARDIAN
    }
}
