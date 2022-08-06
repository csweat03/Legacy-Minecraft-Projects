package com.fbiclient.fbi.client.framework.helper.player;

import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import me.xx.utility.MathUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3d;

public class AngleHelper implements IHelper {

    /**
     * dont ask me i just make simple hacker thingy with quick maths to make mMC aura kthx
     *
     * @param entity    - the entity to value rotations to
     * @param randomize - if u want to randomize the yaw and pitch
     * @return array of yaw and pitch
     */
    public float[] getAngles(Entity entity, boolean randomize) {
        if (entity == null) return null;
        double xDiff = (entity.posX + ((entity.posX - entity.lastTickPosX) * 2)) - mc.thePlayer.posX;
        double zDiff = (entity.posZ + ((entity.posZ - entity.lastTickPosZ) * 2)) - mc.thePlayer.posZ;
        double yDiff;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase) entity;
            yDiff = elb.posY + (elb.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        } else {
            yDiff = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        }
        double hypotenuse = MathHelper.sqrt_double((xDiff * xDiff) + (zDiff * zDiff));
        double small = MathUtility.getRandom(-2.2, 2.5);
        double medium = MathUtility.getRandom(3, 12);
        double kms = randomize ? 90.7 - small : 90;
        double meme = randomize ? medium + small : 0;
        double yaw = (MathHelper.atan2(zDiff, xDiff) * 180D / Math.PI) - kms;
        double pitch = -(MathHelper.atan2(yDiff, hypotenuse) * 180D / Math.PI) + meme;
        return new float[]{(float) yaw,
                (float) pitch};
    }

    public void rotate(UpdateMotionEvent e, float yaw, float pitch, boolean silent) {
        e.setYaw(yaw);
        e.setPitch(pitch);
        if (!silent) {
            mc.thePlayer.rotationYaw = e.getYaw();
            mc.thePlayer.rotationPitch = e.getPitch();
        }
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(mc.getMinecraft().thePlayer.posX, mc.getMinecraft().thePlayer.posY + (double) mc.getMinecraft().thePlayer.getEyeHeight(), mc.getMinecraft().thePlayer.posZ);
    }

    public float[] getNeededRotations(Vec3d vec) {
        Vec3d eyesPos = getEyesPos();
        double diffX = (vec.x - eyesPos.x) + 0.5D;
        double diffY = (vec.y - eyesPos.y) + 0.5D;
        double diffZ = (vec.z - eyesPos.z) + 0.5D;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float) (-(Math.atan2(diffY, diffXZ) * 180.0 / Math.PI));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown() ? 90.0f : MathHelper.wrapAngleTo180_float(pitch)};
    }

    public float getPitchChange(EntityLivingBase entity) {
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double deltaY = entity.posY - 2.2 + (double) entity.getEyeHeight() - mc.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) pitchToEntity);
    }

    public float getYawChange(EntityLivingBase entity) {
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : Math.toDegrees(-Math.atan(deltaX / deltaZ)));
        return MathHelper.wrapAngleTo180_float(-mc.thePlayer.rotationYaw - (float) yawToEntity);
    }

    public static float[] faceTarget(Entity target, float p_70625_2_, float p_70625_3_) {
        float yaw, pitch;
        double var4 = target.posX - mc.thePlayer.posX;
        double var8 = target.posZ - mc.thePlayer.posZ;
        double var6;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var10 = (EntityLivingBase) target;
            var6 = var10.posY + (double) var10.getEyeHeight()
                    - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        } else {
            var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0D
                    - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        }
        double var14 = (double) MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float) (-(Math.atan2(var6 - (target instanceof EntityPlayer ? 0.25 : 0), var14)
                * 180.0D / Math.PI));
        pitch = changeRotation(mc.thePlayer.rotationPitch, var13, p_70625_3_);
        yaw = changeRotation(mc.thePlayer.rotationYaw, var12, p_70625_2_);
        return new float[]{yaw, pitch};
    }

    public static float changeRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_)
            var4 = p_70663_3_;
        if (var4 < -p_70663_3_)
            var4 = -p_70663_3_;
        return p_70663_1_ + var4;
    }

    public static double[] getRotationToEntity(Entity entity) {
        double pX = mc.thePlayer.posX;
        double pY = mc.thePlayer.posY + (mc.thePlayer.getEyeHeight());
        double pZ = mc.thePlayer.posZ;

        double eX = entity.posX;
        double eY = entity.posY + (entity.height/2);
        double eZ = entity.posZ;

        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2) + Math.pow(dZ, 2));

        double yaw = (Math.toDegrees(Math.atan2(dZ, dX)) + 90);
        double pitch = (Math.toDegrees(Math.atan2(dH, dY)));

        return new double[]{yaw, 90 - pitch};
    }
    
    public boolean isWithinFOV(Entity en, float angle) {
        double angleDifference = angleDifference(mc.thePlayer.rotationYaw, ANGLE_HELPER.getRotationToEntity(en)[0]);
        return (angleDifference > 0 && angleDifference < angle) || (-angle < angleDifference && angleDifference < 0);
    }

    public static double angleDifference(double a, double b) {
        return ((((a - b) % 360D) + 540D) % 360D) - 180D;
    }

}
