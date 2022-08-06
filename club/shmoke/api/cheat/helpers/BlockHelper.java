package club.shmoke.api.cheat.helpers;

import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.client.util.math.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.*;

/**
 * @author Kyle
 * @since 9/2/2017
 **/
public class BlockHelper implements IHelper {
	Minecraft mc = Minecraft.getMinecraft();
	public boolean isInLiquid() {
		if (mc.thePlayer == null) {
			return false;
		}

		boolean inLiquid = false;
		int y = (int) mc.thePlayer.boundingBox.minY;

		for (int x = MathUtil.floor_double(mc.thePlayer.boundingBox.minX); x < MathUtil
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
			for (int z = MathUtil.floor_double(mc.thePlayer.boundingBox.minZ); z < MathUtil
					.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
				Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();

				if ((block != null) && (!(block instanceof BlockAir))) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}

					inLiquid = true;
				}
			}
		}

		return inLiquid;
	}

	public Block getBlock(AxisAlignedBB bb) {
		int y = (int) bb.minY;
		for (int x = MathUtil.floor_double(bb.minX); x < MathUtil.floor_double(bb.maxX) + 1; x++) {
			for (int z = MathUtil.floor_double(bb.minZ); z < MathUtil.floor_double(bb.maxZ) + 1; z++) {
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

	public IBlockState getState(BlockPos pos) {
		return Minecraft.getMinecraft().theWorld.getBlockState(pos);
	}

	public Block getBlock(double x, double y, double z) {
		return getState(new BlockPos(x, y, z)).getBlock();
	}

	public Material getMaterial(BlockPos pos) {
		return getBlock(pos).getMaterial();
	}

	public void faceVectorPacketInstant(Vec3 vec) {
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
			Vec3 hitVec = new Vec3(neighbor).addVector(0.5, 0.5, 0.5).add(new Vec3(side2.getDirectionVec()).scale(0.5).normalize());

			yaw = ANGLE_HELPER.getNeededRotations(hitVec)[0];
			pitch = ANGLE_HELPER.getNeededRotations(hitVec)[1];
			if (canBeClicked(neighbor)) {
				return new float[]{yaw, pitch};
			} else {
				hitVec = new Vec3(pos).addVector(0.5, 0.5, 0.5).add(new Vec3(side.getDirectionVec()).scale(0.5).normalize());
				yaw = ANGLE_HELPER.getNeededRotations(hitVec)[0];
				pitch = ANGLE_HELPER.getNeededRotations(hitVec)[1];
				return new float[]{yaw, pitch};
			}
		}
		return new float[]{1.0f, 1.0f};
	}

	public boolean isOnLiquid() {
		if (mc.thePlayer == null) {
			return false;
		}

		boolean onLiquid = false;
		int y = (int) mc.thePlayer.boundingBox.offset(0.0D, -0.01D, 0.0D).minY;

		for (int x = MathUtil.floor_double(mc.thePlayer.boundingBox.minX); x < MathUtil
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
			for (int z = MathUtil.floor_double(mc.thePlayer.boundingBox.minZ); z < MathUtil
					.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
				Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();

				if ((block != null) && (!(block instanceof BlockAir))) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}

					onLiquid = true;
				}
			}
		}

		return onLiquid;
	}

	public boolean isInsideBlock() {
		for (int x = MathUtil.floor_double(mc.thePlayer.boundingBox.minX); x < MathUtil
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
			for (int y = MathUtil.floor_double(mc.thePlayer.boundingBox.minY); y < MathUtil
					.floor_double(mc.thePlayer.boundingBox.maxY) + 1; ++y) {
				for (int z = MathUtil.floor_double(mc.thePlayer.boundingBox.minZ); z < MathUtil
						.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
					final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
					final AxisAlignedBB boundingBox;

					if (block != null && !(block instanceof BlockAir)
							&& (boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z),
									mc.theWorld.getBlockState(new BlockPos(x, y, z)))) != null
							&& mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public Block getBlockUnderPlayer(final EntityPlayer inPlayer, final double height) {
		return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ));
	}

	public Block getBlockAbovePlayer(final EntityPlayer inPlayer, final double height) {
		return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY + inPlayer.height + height, inPlayer.posZ));
	}

	public Block getBlock(final int x, final int y, final int z) {
		return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
	}

	public Block getBlock(final BlockPos pos) {
		return mc.theWorld.getBlockState(pos).getBlock();
	}
}
