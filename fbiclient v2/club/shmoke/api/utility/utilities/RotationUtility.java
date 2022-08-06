package club.shmoke.api.utility.utilities;

import club.shmoke.Client;
import club.shmoke.api.utility.Utility;
import club.shmoke.main.cheats.fight.Killaura;
import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.*;
import optifine.Reflector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Christian
 */
public class RotationUtility {

    private Minecraft mc = Minecraft.getMinecraft();

    public float[] getNeededRotations(Vec3 vec) {
        Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        double diffX = (vec.xCoord - eyesPos.xCoord) + 0.5D;
        double diffY = (vec.yCoord - eyesPos.yCoord) + 0.5D;
        double diffZ = (vec.zCoord - eyesPos.zCoord) + 0.5D;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float) (-(Math.atan2(diffY, diffXZ) * 180.0 / Math.PI));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown() ? 90.0f : MathHelper.wrapAngleTo180_float(pitch)};
    }

    public Entity getClosestEntity() {
        Entity closestEntityP = null;
        Iterator<?> var2 = Minecraft.getMinecraft().theWorld.loadedEntityList.iterator();

        while (var2.hasNext()) {
            Object o = var2.next();
            Entity entityplayerP = (Entity) o;

            if (!(o instanceof EntityPlayerSP) && !entityplayerP.isDead && entityplayerP.isEntityAlive()
                    && entityplayerP != mc.thePlayer
                    && (closestEntityP == null || Minecraft.getMinecraft().thePlayer.getDistanceToEntity(
                    entityplayerP) < Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEntityP))) {
                closestEntityP = entityplayerP;
            }
        }

        return closestEntityP;
    }

    public float[] getRotations(Entity entity) {
        if (entity == null)
            return null;

        double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY;

        if ((entity instanceof EntityLivingBase)) {
            EntityLivingBase elb = (EntityLivingBase) entity;
            diffY = elb.posY + (elb.getEyeHeight() - 0.4)
                    - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D
                    - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[]{yaw, pitch};
    }

    public float[] faceTarget(Entity target, float p_70625_2_, float p_70625_3_, boolean miss) {
        float yaw, pitch;
        double var4 = target.posX - Minecraft.getMinecraft().thePlayer.posX;
        double var8 = target.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double var6;
        if (target instanceof EntityPlayer) {
            EntityPlayer var10 = (EntityPlayer) target;
            var6 = var10.posY + (double) var10.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.posY
                    + (double) Minecraft.getMinecraft().thePlayer.getEyeHeight());
        } else {
            var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0D
                    - (Minecraft.getMinecraft().thePlayer.posY
                    + (double) Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        double var14 = (double) MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float) (-(Math.atan2(var6 - (target instanceof EntityPlayer ? 0.25 : 0), var14) * 180.0D
                / Math.PI));
        pitch = changeRotation(Minecraft.getMinecraft().thePlayer.rotationPitch, var13, p_70625_3_);
        yaw = changeRotation(Minecraft.getMinecraft().thePlayer.rotationYaw, var12, p_70625_2_);
        return new float[]{yaw, pitch};
    }

    public float changeRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_)
            var4 = p_70663_3_;
        if (var4 < -p_70663_3_)
            var4 = -p_70663_3_;
        return p_70663_1_ + var4;
    }

    public float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityEgg var4 = new EntityEgg(mc.theWorld);
        var4.posX = (double) var0 + 0.5D;
        var4.posY = (double) var1 + 0.5D;
        var4.posZ = (double) var2 + 0.5D;
        var4.posX += (double) var3.getDirectionVec().getX() * 0.25D;
        var4.posY += (double) var3.getDirectionVec().getY() * 0.25D;
        var4.posZ += (double) var3.getDirectionVec().getZ() * 0.25D;
        return getDirectionToEntity(var4);
    }

    public float[] kyleIsDopeASF(Entity entity, boolean randomize) {
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
        double small = new Utility().mathUtility.getRandom(-2.2, 2.5);
        double medium = new Utility().mathUtility.getRandom(3, 12);
        double kms = randomize ? 90.7 - small : 90;
        double meme = randomize ? medium + small : 0;
        double yaw = (Math.atan2(zDiff, xDiff) * 180D / Math.PI) - kms;
        double pitch = -(Math.atan2(yDiff, hypotenuse) * 180D / Math.PI) + meme;
        return new float[]{(float) yaw,
                (float) pitch};
    }

    public boolean isWithinFOV(Entity entity, double fov) {
        float[] rotations = getRotationsNeeded(entity);
        float yawDifference = getYawDifference(Minecraft.getMinecraft().thePlayer.rotationYaw % 360F, rotations[0]);
        return yawDifference < fov && yawDifference > -fov;
    }

    public float getYawDifference(float currentYaw, float neededYaw) {
        float yawDifference = neededYaw - currentYaw;

        if (yawDifference > 180) {
            yawDifference = -((360F - neededYaw) + currentYaw);
        } else if (yawDifference < -180) {
            yawDifference = ((360F - currentYaw) + neededYaw);
        }

        return yawDifference;
    }

    public float[] getRotationsNeeded(double x, double y, double z) {
        double xSize = x - Minecraft.getMinecraft().thePlayer.posX;
        double locationMath;
        locationMath = new Utility().mathUtility.getNumberWithinRange(0.7f, 1.4f);
        double ySize = y - (Minecraft.getMinecraft().thePlayer.posY + (double) locationMath);
        double zSize = z - Minecraft.getMinecraft().thePlayer.posZ;
        double theta = (double) MathHelper.sqrt_double(xSize * xSize + zSize * zSize);
        float yaw = (float) (Math.atan2(zSize, xSize) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (-(Math.atan2(ySize, theta) * 180.0D / Math.PI));
        return new float[]{
                (Minecraft.getMinecraft().thePlayer.rotationYaw
                        + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw)) % 360F,
                (Minecraft.getMinecraft().thePlayer.rotationPitch
                        + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch))
                        % 360F,};
    }

    public float[] getRotationsNeeded(Entity entity) {
        if (entity == null)
            return null;

        return getRotationsNeeded(entity.posX, entity.posY + ((double) entity.getEyeHeight() / 2F), entity.posZ);
    }

    private float[] getDirectionToEntity(Entity var0) {
        return new float[]{getYaw(var0) + mc.thePlayer.rotationYaw, getPitch(var0) + mc.thePlayer.rotationPitch};
    }

    public float getYaw(Entity var0) {
        double var1 = var0.posX - mc.thePlayer.posX;
        double var3 = var0.posZ - mc.thePlayer.posZ;
        double var5;

        if (var3 < 0.0D && var1 < 0.0D) {
            var5 = 90.0D + Math.toDegrees(Math.atan(var3 / var1));
        } else if (var3 < 0.0D && var1 > 0.0D) {
            var5 = -90.0D + Math.toDegrees(Math.atan(var3 / var1));
        } else {
            var5 = Math.toDegrees(-Math.atan(var1 / var3));
        }

        return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float) var5));
    }

    public float getPitch(Entity var0) {
        double var1 = var0.posX - mc.thePlayer.posX;
        double var3 = var0.posZ - mc.thePlayer.posZ;
        double var5 = var0.posY - 1.6D + (double) var0.getEyeHeight() - mc.thePlayer.posY;
        double var7 = (double) MathHelper.sqrt_double(var1 * var1 + var3 * var3);
        double var9 = -Math.toDegrees(Math.atan(var5 / var7));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) var9);
    }

    /**
     * Increases the default hit vector ray to be the required distance to connect to enemies bounding box (connects hit vectors)
     *
     * @param entity to be superman lasEr Eyed
     * @return laser eyed entityyyy
     * @author lilquak
     */
    public Entity raycast(Entity entity) {
        /*mc.thePlayer = entity*/
        Entity me = mc.thePlayer;
        
        Vec3 enemyRay = entity.getPositionVector().add(new Vec3(0, entity.getEyeHeight(), 0));
        
        Vec3 myRay = mc.thePlayer.getPositionVector().add(new Vec3(0, mc.thePlayer.getEyeHeight(), 0));
        
        Vec3 hitVector = null;
        /*arisvena faze*/
        AxisAlignedBB a = mc.thePlayer.getEntityBoundingBox().addCoord(enemyRay.xCoord - myRay.xCoord, enemyRay.yCoord - myRay.yCoord, enemyRay.zCoord - myRay.zCoord).expand(1, 1, 1);
        /*arisvena faze but we used it this time*/
        List<Entity> boundingBoxesList = mc.theWorld.getEntitiesWithinAABBExcludingEntity(me, a); /*ya reach retard*/
        double range = 6.5;
        
        Entity enty = null;
        
        for (int i = 0; i < boundingBoxesList.size(); ++i) {
            /*thing we did the looperdooper 2*/
            Entity ent = boundingBoxesList.get(i);
            /*can he be colide?!?!?!? OWOWOWOWOWOWO*/
            if (ent.canBeCollidedWith()) {
                /*ffs why do i describe everything - hm idk why but i did the same thing lol*/
                float borderSize = ent.getCollisionBorderSize();
                AxisAlignedBB enemyBoundingBox = ent.getEntityBoundingBox().expand((double) borderSize, (double) borderSize, (double) borderSize);
                MovingObjectPosition objectPosition = enemyBoundingBox.calculateIntercept(myRay, enemyRay);
                /*checks if my superman eyeS r luk i think*/
                if (enemyBoundingBox.isVecInside(myRay)) {
                    if (0.0D < range || range == 0.0D) {
                        enty = ent;
                        hitVector = objectPosition == null ? myRay : objectPosition.hitVec;
                        range = 0.0D;
                    }
                } else if (objectPosition != null) {
                    double distanceToEntity = myRay.distanceTo(objectPosition.hitVec);
                    if (distanceToEntity < range || range == 0.0D) {
                        enty = ent;
                        hitVector = objectPosition.hitVec;
                        range = distanceToEntity;
                    }
                }
            }
        }
        return enty; 
    }

    public static Vec3 getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3((double) (f1 * f2), (double) f3, (double) (f * f2));
    }
}
