package club.shmoke.api.cheat.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathUtil;
import net.minecraft.util.Vec3;
import club.shmoke.client.util.math.LocationHelper;
import club.shmoke.client.util.math.MathHelper;

import java.util.Iterator;
import java.util.Random;

public class AngleHelper {

    static Minecraft mc = Minecraft.getMinecraft();

    public static Vec3 getEyesPos() {
        return new Vec3(mc.getMinecraft().thePlayer.posX, mc.getMinecraft().thePlayer.posY + (double) mc.getMinecraft().thePlayer.getEyeHeight(), mc.getMinecraft().thePlayer.posZ);
    }

    public float[] getNeededRotations(Vec3 vec) {
        Vec3 eyesPos = getEyesPos();
        double diffX = (vec.xCoord - eyesPos.xCoord) + 0.5D;
        double diffY = (vec.yCoord - eyesPos.yCoord) + 0.5D;
        double diffZ = (vec.zCoord - eyesPos.zCoord) + 0.5D;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float) (-(Math.atan2(diffY, diffXZ) * 180.0 / Math.PI));
        return new float[]{MathUtil.wrapAngleTo180_float(yaw), Minecraft.getMinecraft().gameSettings.keyBindJump.pressed ? 90.0f : MathUtil.wrapAngleTo180_float(pitch)};
    }

    public static Entity getClosestEntity() {
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

    public static float[] getFacePosEntityRemote(EntityLivingBase facing, Entity en) {
        if (en == null) {
            return new float[]{facing.rotationYawHead, facing.rotationPitch};
        }
        return getFacePosRemote(new Vec3(facing.posX, facing.posY + en.getEyeHeight(), facing.posZ),
                new Vec3(en.posX, en.posY + en.getEyeHeight(), en.posZ));
    }

    /**
     * @return index 0 = yaw | index 1 = pitch
     */
    public static float[] getFacePosRemote(Vec3 src, Vec3 dest) {
        double diffX = dest.xCoord - src.xCoord;
        double diffY = dest.yCoord - (src.yCoord);
        double diffZ = dest.zCoord - src.zCoord;
        double dist = MathUtil.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[]{MathUtil.wrapAngleTo180_float(yaw), MathUtil.wrapAngleTo180_float(pitch)};
    }

    public static float[] kyleIsActuallyFuckingAwesomeForGivingTheseRotationsToMe(Entity e) {
        Minecraft mc = Minecraft.getMinecraft();
        return new float[]{getYawChangeToEntity(e) + mc.thePlayer.rotationYaw,
                getPitchChangeToEntity(e) + mc.thePlayer.rotationPitch};
    }

    public static float[] getRotations(LocationHelper location) {
        double diffX = location.getX() + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        double diffZ = location.getZ() + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY = (location.getY() + 0.5) / 2.0D
                - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double dist = MathUtil.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[]{yaw, pitch};
    }

    public static float[] getRotations(Entity entity) {
        if (entity == null) {
            return null;
        }

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

        double dist = MathUtil.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[]{yaw, pitch};
    }

    public static float getYawChangeToEntity(Entity entity) {
        Minecraft mc = Minecraft.getMinecraft();
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double yawToEntity;

        if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
            yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
                yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
            } else {
                yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
            }
        }

        return MathUtil.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float) yawToEntity));
    }

    public static float getPitchChangeToEntity(Entity entity) {
        Minecraft mc = Minecraft.getMinecraft();
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - 0.4 - mc.thePlayer.posY;
        double distanceXZ = MathUtil.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathUtil.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) pitchToEntity);
    }

    public static float[] faceTarget(Entity target, float p_70625_2_, float p_70625_3_, boolean miss) {
        float yaw, pitch;
        double var4 = target.posX - Minecraft.getMinecraft().thePlayer.posX;
        double var8 = target.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double var6;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var10 = (EntityLivingBase) target;
            var6 = var10.posY + (double) var10.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.posY
                    + (double) Minecraft.getMinecraft().thePlayer.getEyeHeight());
        } else {
            var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0D
                    - (Minecraft.getMinecraft().thePlayer.posY
                    + (double) Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        Random rnd = new Random();
        double var14 = (double) MathUtil.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float) (-(Math.atan2(var6 - (target instanceof EntityPlayer ? 0.25 : 0), var14) * 180.0D
                / Math.PI));
        pitch = changeRotation(Minecraft.getMinecraft().thePlayer.rotationPitch, var13, p_70625_3_);
        yaw = changeRotation(Minecraft.getMinecraft().thePlayer.rotationYaw, var12, p_70625_2_);
        return new float[]{yaw, pitch};
    }

    public static float changeRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float var4 = MathUtil.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_)
            var4 = p_70663_3_;
        if (var4 < -p_70663_3_)
            var4 = -p_70663_3_;
        return p_70663_1_ + var4;
    }

    public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
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

    public float[] kyleIsActuallyFuckingAwesomeForGivingTheseRotationsToMe(Entity entity, boolean randomize) {
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
        double hypotenuse = MathUtil.sqrt_double((xDiff * xDiff) + (zDiff * zDiff));
        double small = MathHelper.getRandom(-2.2, 2.5);
        double medium = MathHelper.getRandom(3, 12);
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

    ///////////////////////////////////////////

    public float[] getRotationsNeeded(double x, double y, double z) {
        double xSize = x - Minecraft.getMinecraft().thePlayer.posX;
        double locationMath = 0.0;
        locationMath = MathHelper.getNumberWithinRange(0.7f, 1.4f);
        double ySize = y - (Minecraft.getMinecraft().thePlayer.posY + (double) locationMath);
        double zSize = z - Minecraft.getMinecraft().thePlayer.posZ;
        double theta = (double) MathUtil.sqrt_double(xSize * xSize + zSize * zSize);
        float yaw = (float) (Math.atan2(zSize, xSize) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (-(Math.atan2(ySize, theta) * 180.0D / Math.PI));
        return new float[]{
                (Minecraft.getMinecraft().thePlayer.rotationYaw
                        + MathUtil.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw)) % 360F,
                (Minecraft.getMinecraft().thePlayer.rotationPitch
                        + MathUtil.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch))
                        % 360F,};
    }

    public float[] getRotationsNeeded(Entity entity) {
        if (entity == null)
            return null;

        return getRotationsNeeded(entity.posX, entity.posY + ((double) entity.getEyeHeight() / 2F), entity.posZ);
    }

    private static float[] getDirectionToEntity(Entity var0) {
        return new float[]{getYaw(var0) + mc.thePlayer.rotationYaw, getPitch(var0) + mc.thePlayer.rotationPitch};
    }

    public float[] getRotationNeededForBlock(EntityPlayer paramEntityPlayer, BlockPos pos) {
        double d1 = pos.getX() - paramEntityPlayer.posX;
        double d2 = pos.getY() + 0.5 - (paramEntityPlayer.posY + paramEntityPlayer.getEyeHeight());
        double d3 = pos.getZ() - paramEntityPlayer.posZ;
        double d4 = Math.sqrt(d1 * d1 + d3 * d3);
        float f1 = (float) (Math.atan2(d3, d1) * 180.0D / Math.PI) - 90.0F;
        float f2 = (float) -(Math.atan2(d2, d4) * 180.0D / Math.PI);
        return new float[]{f1, f2};
    }

    public static float getYaw(Entity var0) {
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

        return MathUtil.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float) var5));
    }

    public static float getPitch(Entity var0) {
        double var1 = var0.posX - mc.thePlayer.posX;
        double var3 = var0.posZ - mc.thePlayer.posZ;
        double var5 = var0.posY - 1.6D + (double) var0.getEyeHeight() - mc.thePlayer.posY;
        double var7 = (double) MathUtil.sqrt_double(var1 * var1 + var3 * var3);
        double var9 = -Math.toDegrees(Math.atan(var5 / var7));
        return -MathUtil.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) var9);
    }
}
