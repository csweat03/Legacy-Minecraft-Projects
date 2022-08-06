package club.shmoke.client.cheats.movement;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.api.event.Event;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.Client;
import club.shmoke.client.cheats.combat.Killaura;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathUtil;

public class Speed extends Cheat implements IHelper {
    public static boolean Speed = true;
    public int staged;
    public Property<Type> type = new Property<>(this, "Type", Type.SLOWHOP);
    public Property<Integer> speed = new Property<>(this, "Speed", 4, 1, 10, 1);
    public Property<Double> cy = new Property<>(this, "PosY", 0.42, 0.0, 1.00000000000, 0.00000000001);
    public Property<Double> cnegY = new Property<>(this, "NegY", 0.42, 0.0, 1.00000000000, 0.00000000001);
    public Property<Double> cspeed = new Property<>(this, "Speed", 0.26, 0.0, 5.0, 0.00000000001);
    public Property<Double> ctimer = new Property<>(this, "Timer", 1.0, 0.1, 10.0, 0.1);
    public Property<Boolean> cnegative = new Property<>(this, "Negative", false);
    private Property<String> customSpeeds = new Property<>(this, "Custom Speed");

    public Speed() {
        super("Speed", Cheat.Type.MOVEMENT);
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1f;
        staged = 0;
        mc.gameSettings.viewBobbing = true;
        mc.gameSettings.keyBindJump.pressed = false;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        staged = 1;
        super.onEnable();
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent event) {
        if (type.getValue() == Type.CUSTOM) {
            if (mc.thePlayer.isMoving()) {
                if (mc.thePlayer.onGround)
                    mc.thePlayer.motionY = cy.getValue();
                else if (cnegative.getValue())
                    mc.thePlayer.motionY = -cnegY.getValue();
                mc.thePlayer.setSpeed(cspeed.getValue());
                mc.timer.timerSpeed = (float) (ctimer.getValue() + 0F);
            }
        } else {
            if (flight.getState() || mc.thePlayer.fallDistance >= 10F || !mc.thePlayer.isMoving())
                return;
            mc.gameSettings.viewBobbing = type.getValue() != Type.TIMER;

            switch (type.getValue()) {
                case SLOWHOP:
                    mc.thePlayer.jump2();
                    break;
                case TIMER:
                    mc.timer.timerSpeed = speed.getValue();
                    break;
                case KOHIHOP:
                    mc.thePlayer.setSpeed(mc.thePlayer.getSpeed());
                    for (EntityPlayer e : mc.theWorld.playerEntities) {
                        if (mc.thePlayer.getDistanceToEntity(e) <= 6 && e != mc.thePlayer
                                && Client.INSTANCE.getCheatManager().get(Killaura.class).getState()) {
                            mc.timer.timerSpeed = 1;
                            return;
                        }
                    }
                    if (event.type == Event.Type.PRE) {
                        mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 0.035);
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.motionY = 0.4;
                            mc.thePlayer.setPosition(mc.thePlayer.posX + (mc.thePlayer.motionX * 2), mc.thePlayer.posY,
                                    mc.thePlayer.posZ + (mc.thePlayer.motionZ * 2));
                        } else if (mc.thePlayer.motionY < -0.3
                                && !(BLOCK_HELPER.getBlockUnderPlayer(mc.thePlayer, 1) instanceof BlockAir)) {
                            mc.thePlayer.motionY = -0.5;
                            mc.thePlayer.setSpeed(0.6);
                        } else if (mc.thePlayer.motionY > 0.2 && !mc.thePlayer.onGround) {
                            mc.timer.timerSpeed = 2F;
                        } else {
                            mc.timer.timerSpeed = 1F;
                            mc.thePlayer.setSpeed(0.6);
                        }
                    }
                    break;
                case KOHIGROUND:
                    if (BLOCK_HELPER.getBlockUnderPlayer(mc.thePlayer, 1) instanceof BlockAir
                            || mc.gameSettings.keyBindJump.pressed) {
                        mc.thePlayer.setSpeed(0);
                        mc.timer.timerSpeed = 1F;
                        mc.thePlayer.motionY = -5;
                        return;
                    } else {
                        mc.thePlayer.setSpeed(mc.thePlayer.getSpeed());
                        if (event.type == Event.Type.PRE) {
                            mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 0.035);
                            if (mc.thePlayer.onGround)
                                mc.thePlayer.motionY = 0.07;
                            else if (mc.thePlayer.motionY < -0.05) {
                                mc.thePlayer.motionY = -0.8;
                                mc.thePlayer.setSpeed(0.6);
                            } else if (mc.thePlayer.motionY > 0.01) {
                                mc.thePlayer.setSpeed(1);
                            } else {
                                mc.timer.timerSpeed = mc.thePlayer.ticksExisted % 2 == 0 ? 1.6F : 1.1F;
                                mc.thePlayer.setSpeed(1.6);
                            }
                        }
                    }
                    break;
                case OLDGUARDIAN:
                    ENTITY_HELPER.setSpeed(ENTITY_HELPER.getSpeed());

                    if ((mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindLeft.pressed
                            || mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindBack.pressed)) {
                        final float direction = mc.thePlayer.rotationYaw + ((mc.thePlayer.moveForward < 0.0f) ? 180 : 0)
                                + ((mc.thePlayer.moveStrafing > 0.0f) ? (-90.0f * ((mc.thePlayer.moveForward < 0.0f) ? -0.5f
                                : ((mc.thePlayer.moveForward > 0.0f) ? 0.5f : 1.0f))) : 0.0f)
                                - ((mc.thePlayer.moveStrafing < 0.0f) ? (-90.0f * ((mc.thePlayer.moveForward < 0.0f) ? -0.5f
                                : ((mc.thePlayer.moveForward > 0.0f) ? 0.5f : 1.0f))) : 0.0f);
                        final float xDir = (float) Math.cos((direction + 90.0f) * 3.141592653589793 / 180.0);
                        final float zDir = (float) Math.sin((direction + 90.0f) * 3.141592653589793 / 180.0);

                        if (mc.thePlayer.onGround)
                            mc.thePlayer.jump2();

                        if (mc.thePlayer.motionY == 0.33319999363422365
                                && (mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindLeft.pressed
                                || mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindBack.pressed)) {
                            mc.timer.timerSpeed = 2.0f;
                            mc.thePlayer.motionX = xDir * (speed.getValue()) * 0.6;
                            mc.thePlayer.motionZ = zDir * (speed.getValue()) * 0.6;
                        } else {
                            mc.timer.timerSpeed = 1.0f;
                        }
                    }
                    break;
                case GWEN:
                    mc.thePlayer.jump2();
                    if (BLOCK_HELPER.getBlockUnderPlayer(mc.thePlayer, 0.01) instanceof BlockAir) {
                        mc.thePlayer.setSpeed(0.352);
                    }
                    break;
                case HYPIXEL:
                    if (!(BLOCK_HELPER.isOnLiquid()) || BLOCK_HELPER.isInLiquid()) {
                        if (mc.thePlayer.onGround) {
                            mc.gameSettings.keyBindJump.pressed = false;
                            mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? 0.4 : 0.36);
                            mc.thePlayer.jump2();
                        } else
                            mc.thePlayer.setSpeed(mc.thePlayer.getSpeed());

                        if (!mc.thePlayer.isMoving()) {
                            mc.timer.timerSpeed = 1.0f;
                            mc.thePlayer.motionX = 0.0;
                            mc.thePlayer.motionZ = 0.0;
                        }
                    }
                    break;
                case HYPIXELGROUND:
                    if (mc.thePlayer.onGround) {
                        if (mc.thePlayer.ticksExisted % 3 == 0) {
                            event.y += 0.4;
                        }
                        mc.timer.timerSpeed = mc.thePlayer.ticksExisted % 2 == 0 ? 1.5F : 1;
                        if (mc.thePlayer.posY + 0.1 < event.y)
                            mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? 0.24 : 0.18);
                    }
                default:
                    break;
            }
        }
    }

    public enum Type {
        SLOWHOP, TIMER, HYPIXEL, HYPIXELGROUND, GWEN, OLDGUARDIAN, KOHIHOP, KOHIGROUND, CUSTOM
    }
}
