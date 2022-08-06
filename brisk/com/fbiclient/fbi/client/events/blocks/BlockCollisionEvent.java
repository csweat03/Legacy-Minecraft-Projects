package com.fbiclient.fbi.client.events.blocks;

import me.xx.api.event.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
/**
 * @author Kyle
 * @since 2/2/2018
 **/
public class BlockCollisionEvent extends Event {

	private Block block;
	private BlockPos pos;
	private AxisAlignedBB boundingBox;

	public BlockCollisionEvent(Block block, BlockPos pos, AxisAlignedBB boundingBox) {
		this.block = block;
		this.pos = pos;
		this.boundingBox = boundingBox;
	}

	public Block getBlock() {
		return block;
	}

	public BlockPos getPos() {
		return pos;
	}

	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}

}
