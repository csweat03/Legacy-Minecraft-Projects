package club.shmoke.api.utility.utilities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

/**
 * @author Christian
 */
public class BlockUtility {

    private Minecraft mc = Minecraft.getMinecraft();

    public boolean isInLiquid() {
        if (mc.thePlayer == null) {
            return false;
        }

        boolean inLiquid = false;
        int y = (int) mc.thePlayer.boundingBox.minY;

        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
                .floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
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

    public IBlockState getState(BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(pos);
    }

    public Block getBlock(double x, double y, double z) {
        return getState(new BlockPos(x, y, z)).getBlock();
    }

    public Material getMaterial(BlockPos pos) {
        return getBlock(pos).getMaterial();
    }

    public void faceVector(Vec3 vec) {
        //float[] rotations = AngleHelper.GET.getNeededRotations(vec);
        //mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(rotations[0], rotations[1], Minecraft.getMinecraft().thePlayer.onGround));
    }

    public boolean isOnLiquid() {
        if (mc.thePlayer == null) {
            return false;
        }

        boolean onLiquid = false;
        int y = (int) mc.thePlayer.boundingBox.offset(0.0D, -0.01D, 0.0D).minY;

        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
                .floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
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
        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
                .floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(mc.thePlayer.boundingBox.minY); y < MathHelper
                    .floor_double(mc.thePlayer.boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
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

    public Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
        return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ));
    }

    public Block getBlockAbovePlayer(EntityPlayer inPlayer, double height) {
        return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY + inPlayer.height + height, inPlayer.posZ));
    }

    public Block getBlock(final int x, final int y, final int z) {
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public Block getBlock(final BlockPos pos) {
        return mc.theWorld.getBlockState(pos).getBlock();
    }

}
