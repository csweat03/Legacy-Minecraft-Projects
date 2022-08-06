package com.fbiclient.fbi.client.framework.helper.player;

import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.impl.Brisk;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class EntityHelper implements IHelper {

	public double getDistanceToFall(Entity e) {
        double distance = 0;
        for (double i = e.posY; i > 0; i -= 0.1) {
            if (i < 0)
                break;
            Block block = BLOCK_HELPER.getBlock(new BlockPos(e.posX, i, e.posZ));
            if (block.getMaterial() != Material.air && (block.isCollidable())
                    && (block.isFullBlock() || block instanceof BlockSlab || block instanceof BlockBarrier
                    || block instanceof BlockStairs || block instanceof BlockGlass
                    || block instanceof BlockStainedGlass)) {
                if (block instanceof BlockSlab)
                    i -= 0.5;
                distance = i;
                break;
            }
        }
        return (e.posY - distance);
    }

    public Entity getClosestToCrosshair(double range) {
        Entity e = null;

        double best = 360;

        for (Entity ent : mc.theWorld.getLoadedEntityList()) {
            if (!(ent instanceof EntityPlayer))
                continue;

            double diffX = ent.posX - Minecraft.getMinecraft().thePlayer.posX;
            double diffZ = ent.posZ - Minecraft.getMinecraft().thePlayer.posZ;
            float newYaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90);
            double difference = Math.abs(ANGLE_HELPER.angleDifference(newYaw, Minecraft.getMinecraft().thePlayer.rotationYaw));

            if (ent != Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().thePlayer.getDistanceToEntity(ent) <= range && ent instanceof EntityPlayer && !Brisk.INSTANCE.getFriendManager().isFriend(ent.getUniqueID())) {
                if (difference < best) {
                    best = difference;
                    e = ent;
                }
            }
        }
        return e;
    }

}
