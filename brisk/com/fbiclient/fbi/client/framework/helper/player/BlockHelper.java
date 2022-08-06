package com.fbiclient.fbi.client.framework.helper.player;

import com.fbiclient.fbi.client.framework.helper.IHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.*;

public class BlockHelper implements IHelper {

	public IBlockState getState(BlockPos pos) {
		return Minecraft.getMinecraft().theWorld.getBlockState(pos);
	}

	public Block getBlock(double x, double y, double z) {
		return getState(new BlockPos(x, y, z)).getBlock();
	}

	public Material getMaterial(BlockPos pos) {
		return getBlock(pos).getMaterial();
	}

	public Block getBlock(BlockPos pos) {
		return getState(pos).getBlock();
	}

	public Block getBlock(AxisAlignedBB bb) {
		int y = (int) bb.minY;
		for (int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; z++) {
				Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null)
					return block;
			}
		}
		return null;
	}

	public boolean canBeClicked(BlockPos pos) {
		return getBlock(pos).canCollideCheck(getState(pos), false);
	}

	public void faceVectorPacketInstant(Vec3d vec) {
		float[] rotations = ANGLE_HELPER.getNeededRotations(vec);
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(rotations[0], rotations[1], Minecraft.getMinecraft().thePlayer.onGround));
	}

	public float[] aimAtBlock(BlockPos pos) {
		EnumFacing[] arrenumFacing = EnumFacing.values();
		int n = arrenumFacing.length;
		int n2 = 0;
		float yaw, pitch;
		while (n2 <= n) {
			EnumFacing side = arrenumFacing[n2];
			BlockPos neighbor = pos.offset(side);
			EnumFacing side2 = side.getOpposite();
			Vec3d hitVec = new Vec3d(neighbor).addVector(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5).normalize());

			yaw = ANGLE_HELPER.getNeededRotations(hitVec)[0];
			pitch = ANGLE_HELPER.getNeededRotations(hitVec)[1];
			if (canBeClicked(neighbor)) {
				return new float[]{yaw, pitch};
			} else {
				hitVec = new Vec3d(pos).addVector(0.5, 0.5, 0.5).add(new Vec3d(side.getDirectionVec()).scale(0.5).normalize());
				yaw = ANGLE_HELPER.getNeededRotations(hitVec)[0];
				pitch = ANGLE_HELPER.getNeededRotations(hitVec)[1];
				return new float[]{yaw, pitch};
			}
		}
		return new float[]{1.0f, 1.0f};
	}
}
