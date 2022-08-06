package com.fbiclient.fbi.impl.cheats.motion;

import me.valkyrie.api.value.Val;
import me.valkyrie.api.value.types.constrain.Clamp;
import me.valkyrie.api.value.types.constrain.Increment;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.game.TickEvent;
import com.fbiclient.fbi.client.events.player.MoveEvent;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import com.fbiclient.fbi.impl.cheats.motion.speed.SpeedModes;
import me.xx.utility.Stopwatch;
import net.minecraft.client.Minecraft;

@CheatManifest(category = Category.MOTION, description = "Move faster", label = "Speed")
public class Speed extends Cheat implements SpeedModes {

    @Val(label = "Mode", description = "The methods of ")
    public Mode mode = Mode.HYPIXEL;

    @Val(label = "Boost", description = "How much boost for the ")
    @Clamp(min = "0.5", max = "3")
    @Increment(value = "0.1")
    public double speed = 1.5;
    public Stopwatch timer = new Stopwatch();

    public boolean quick;
    private int stage, delay;
    private double lastDist, moveSpeed;

    @Register
    public void handleTicking(TickEvent event) {
        mc.timer.updateTimer();
        suffix = (mode.name());
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer.isMoving())
            mc.thePlayer.setSpeed(MOTION_HELPER.getBaseMoveSpeed());
        mc.timer.timerSpeed = 1;
        moveSpeed = 0;
    }

    @Override
    public void onEnable() {
        this.lastDist = stage = 0;
    }

    @Register
    public void handleUpdate(UpdateMotionEvent event) {
        switch (event.getType()) {
            case PRE: {
                switch (mode) {
                    case MINEMAN:
                        MINEMAN.zoom();
                        break;
                    case MINEPLEX:
                        if (mc.thePlayer.isMoving()) {
                            mc.thePlayer.motionY -= 0.025;
                            if (mc.thePlayer.onGround) {
                                mc.thePlayer.motionY = 0.45;
                            }
                            mc.timer.timerSpeed = 0.9f;
                            mc.thePlayer.setSpeed(0.39);
                        }
                        break;
                }
                if (mode == Mode.NCP || mode == Mode.HYPIXEL || mode == Mode.MINEPLEX) {
                    double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
                    double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
                    this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
                }
            }
        }

    }

    @Register
    public void handleMovement(MoveEvent event) {
//        switch (mode) {
//            case MINEPLEX: {
//                if (mc.thePlayer.onGround)
//                    mc.thePlayer.jump();
//            }
//        }

////                mc.timer.timerSpeed = 1f;
////                if ((mc.thePlayer.moveForward == 0.0F) && (mc.thePlayer.moveStrafing == 0.0F)) {
////                    this.moveSpeed = MOTION_HELPER.getBaseMoveSpeed();
////                }
////                if (MathUtility.round(mc.thePlayer.posY - (int) mc.thePlayer.posY, 3) == MathUtility.round(0.41D, 3)) {
////                    event.setY(mc.thePlayer.motionY = 0.38D);
////                } else if (MathUtility.round(mc.thePlayer.posY - (int) mc.thePlayer.posY, 3) == MathUtility.round(0.71D,
////                        3)) {
////                    event.setY(mc.thePlayer.motionY = 0.06D);
////                } else if (MathUtility.round(mc.thePlayer.posY - (int) mc.thePlayer.posY, 3) == MathUtility.round(0.75D,
////                        3)) {
////                    event.setY(mc.thePlayer.motionY = -0.2D);
////                }
////                List collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
////                        mc.thePlayer.boundingBox.offset(0.0D, -0.56D, 0.0D));
////                if ((collidingList.size() > 0) && (MathUtility.round(mc.thePlayer.posY - (int) mc.thePlayer.posY,
////                        3) == MathUtility.round(0.55D, 3))) {
////                    event.setY(-0.12);
////                }
////                if ((stage == 1) && (mc.thePlayer.isCollidedVertically)
////                        && ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F))) {
////                    this.moveSpeed = (2 * MOTION_HELPER.getBaseMoveSpeed() - 0.01);
////                }
////                if ((stage == 2) && (mc.thePlayer.isCollidedVertically)
////                        && ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F))) {
////                    event.setY(mc.thePlayer.motionY = 0.4D);
////                    this.moveSpeed *= 1.4533D;
////                } else if (stage == 3) {
////                    double difference = 0.66D * (this.lastDist - MOTION_HELPER.getBaseMoveSpeed());
////                    this.moveSpeed = (this.lastDist - difference);
////                } else {
////                    List collidingList2 = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
////                            mc.thePlayer.boundingBox.offset(0.0D, mc.thePlayer.motionY, 0.0D));
////                    if (((collidingList2.size() > 0) || (mc.thePlayer.isCollidedVertically)) && (stage > 0)) {
////                        stage = (mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F) ? 1 : 0;
////                    }
////                    this.moveSpeed = (this.lastDist - this.lastDist / 159.0D);
////                }
////                this.moveSpeed = Math.max(this.moveSpeed, MOTION_HELPER.getBaseMoveSpeed());
////                if (stage > 0) {
////                    double forward = mc.thePlayer.movementInput.moveForward;
////                    double strafe = mc.thePlayer.movementInput.moveStrafe;
////                    float yaw = mc.thePlayer.rotationYaw;
////                    if ((forward == 0.0D) && (strafe == 0.0D)) {
////                        event.setX(0.0D);
////                        event.setZ(0.0D);
////                    } else {
////                        if (forward != 0.0D) {
////                            if (strafe > 0.0D) {
////                                yaw += (forward > 0.0D ? -45 : 45);
////                            } else if (strafe < 0.0D) {
////                                yaw += (forward > 0.0D ? 45 : -45);
////                            }
////                            strafe = 0.0D;
////                            if (forward > 0.0D) {
////                                forward = 1.0D;
////                            } else if (forward < 0.0D) {
////                                forward = -1.0D;
////                            }
////                        }
////                        event.setX(forward * this.moveSpeed * Math.cos(Math.toRadians(yaw + 90.0F))
////                                + strafe * this.moveSpeed * Math.sin(Math.toRadians(yaw + 90.0F)));
////                        event.setZ(forward * this.moveSpeed * Math.sin(Math.toRadians(yaw + 90.0F))
////                                - strafe * this.moveSpeed * Math.cos(Math.toRadians(yaw + 90.0F)));
////                    }
////                }
////                if ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F)) {
////                    stage += 1;
////                }
////                double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
////                double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
////                this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
////                break;
//                break;
//            }
//            case HYPIXEL: {
//                /*
//                 * if (mc.thePlayer.onGround) mc.timer.timerSpeed = 0.9f; else
//                 * mc.timer.timerSpeed = 1f;
//                 *
//                 * if (stage == 1 && (mc.thePlayer.moveForward != 0.0f ||
//                 * mc.thePlayer.moveStrafing != 0.0f)) { this.moveSpeed = 1.3 *
//                 * MOTION_HELPER.getBaseMoveSpeed() - 0.01; } else if (stage == 2 &&
//                 * (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
//                 * mc.thePlayer.jump(); this.moveSpeed *= (quick ? 1.682 : 1.375); } else if
//                 * (stage == 3) { double difference = 0.66 * (this.lastDist -
//                 * MOTION_HELPER.getBaseMoveSpeed()); this.moveSpeed = this.lastDist -
//                 * difference; quick = !quick; } else { List collidingList =
//                 * mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
//                 * mc.thePlayer.boundingBox.expand(0.0, mc.thePlayer.motionY, 0.0)); if
//                 * ((collidingList.size() > 0 || mc.thePlayer.isCollidedVertically) && stage >
//                 * 0) { stage = ((mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing
//                 * != 0.0f) ? 1 : 0); } this.moveSpeed = this.lastDist - this.lastDist / 159.0;
//                 * } setMoveSpeed(event, this.moveSpeed = Math.max(this.moveSpeed,
//                 * MOTION_HELPER.getBaseMoveSpeed())); if (mc.thePlayer.moveForward != 0.0f ||
//                 * mc.thePlayer.moveStrafing != 0.0f) { ++stage; break; } break;
//                 */
//                if ((mc.thePlayer.moveForward == 0.0F) && (mc.thePlayer.moveStrafing == 0.0F)) {
//                    this.moveSpeed = MOTION_HELPER.getBaseMoveSpeed();
//                }
//                if (MathUtility.round(mc.thePlayer.posY - (int) mc.thePlayer.posY, 3) == MathUtility.round(0.4D, 3)) {
//                    event.setY(mc.thePlayer.motionY = 0.31D);
//                } else if (MathUtility.round(mc.thePlayer.posY - (int) mc.thePlayer.posY, 3) == MathUtility.round(0.71D,
//                        3)) {
//                    event.setY(mc.thePlayer.motionY = 0.04D);
//                } else if (MathUtility.round(mc.thePlayer.posY - (int) mc.thePlayer.posY, 3) == MathUtility.round(0.75D,
//                        3)) {
//                    event.setY(mc.thePlayer.motionY = -0.2D);
//                }
//                List collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
//                        mc.thePlayer.boundingBox.offset(0.0D, -0.56D, 0.0D));
//                if ((collidingList.size() > 0) && (MathUtility.round(mc.thePlayer.posY - (int) mc.thePlayer.posY,
//                        3) == MathUtility.round(0.55D, 3))) {
//                    event.setY(-0.14D);
//                }
//                if ((stage == 1) && (mc.thePlayer.isCollidedVertically)
//                        && ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F))) {
//                    this.moveSpeed = (2.05D * MOTION_HELPER.getBaseMoveSpeed() - 0.01D);
//                }
//                if ((stage == 2) && (mc.thePlayer.isCollidedVertically)
//                        && ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F))) {
//                    event.setY(mc.thePlayer.motionY = 0.4D);
//                    this.moveSpeed *= 1.5533D;
//                } else if (stage == 3) {
//                    double difference = 0.66D * (this.lastDist - MOTION_HELPER.getBaseMoveSpeed());
//                    this.moveSpeed = (this.lastDist - difference);
//                } else {
//                    List collidingList2 = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
//                            mc.thePlayer.boundingBox.offset(0.0D, mc.thePlayer.motionY, 0.0D));
//                    if (((collidingList2.size() > 0) || (mc.thePlayer.isCollidedVertically)) && (stage > 0)) {
//                        stage = (mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F) ? 1 : 0;
//                    }
//                    this.moveSpeed = (this.lastDist - this.lastDist / 159.0D);
//                }
//                this.moveSpeed = Math.max(this.moveSpeed, MOTION_HELPER.getBaseMoveSpeed());
//                if (stage > 0) {
//                    double forward = mc.thePlayer.movementInput.moveForward;
//                    double strafe = mc.thePlayer.movementInput.moveStrafe;
//                    float yaw = mc.thePlayer.rotationYaw;
//                    if ((forward == 0.0D) && (strafe == 0.0D)) {
//                        event.setX(0.0D);
//                        event.setZ(0.0D);
//                    } else {
//                        if (forward != 0.0D) {
//                            if (strafe > 0.0D) {
//                                yaw += (forward > 0.0D ? -45 : 45);
//                            } else if (strafe < 0.0D) {
//                                yaw += (forward > 0.0D ? 45 : -45);
//                            }
//                            strafe = 0.0D;
//                            if (forward > 0.0D) {
//                                forward = 1.0D;
//                            } else if (forward < 0.0D) {
//                                forward = -1.0D;
//                            }
//                        }
//                        event.setX(forward * this.moveSpeed * Math.cos(Math.toRadians(yaw + 90.0F))
//                                + strafe * this.moveSpeed * Math.sin(Math.toRadians(yaw + 90.0F)));
//                        event.setZ(forward * this.moveSpeed * Math.sin(Math.toRadians(yaw + 90.0F))
//                                - strafe * this.moveSpeed * Math.cos(Math.toRadians(yaw + 90.0F)));
//                    }
//                }
//                if ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F)) {
//                    stage += 1;
//                }
//                double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
//                double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
//                this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
//                break;
//            }
//            case NCP: {
//                if (this.stage == 1.0 && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
//                    this.moveSpeed = 1.3 * MOTION_HELPER.getBaseMoveSpeed() - 0.01;
//                } else if (this.stage == 2.0 && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
//                    event.y = (mc.thePlayer.motionY = 0.4);
//                    this.moveSpeed *= (mc.thePlayer.ticksExisted % 2 == 0 ? 1.6 : 1.4);
//                } else if (this.stage == 3.0) {
//                    double difference = 0.69 * (this.lastDist - MOTION_HELPER.getBaseMoveSpeed());
//                    this.moveSpeed = this.lastDist - difference;
//                } else {
//                    List collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
//                            mc.thePlayer.boundingBox.offset(0.0, mc.thePlayer.motionY, 0.0));
//                    if ((collidingList.size() > 0 || mc.thePlayer.isCollidedVertically) && this.stage > 0.0) {
//                        this.stage = ((mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) ? 1 : 0);
//                    }
//                    this.moveSpeed = this.lastDist - this.lastDist / 159.0;
//                }
//                mc.thePlayer.setSpeed(this.moveSpeed = Math.max(this.moveSpeed, MOTION_HELPER.getBaseMoveSpeed()));
//                if (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) {
//                    ++this.stage;
//                }
//                break;
//            }
//        }
    }

    public static void setMoveSpeed(MoveEvent event, double speed) {
        double forward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
        double strafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f))
                    + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f))
                    - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }

    public enum Mode {
        NCP, HYPIXEL, MINEPLEX, MINEMAN
    }

}
