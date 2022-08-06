package club.shmoke.client.events.block;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import club.shmoke.api.event.Event;
import club.shmoke.client.util.math.LocationHelper;

public class BoundingBoxEvent extends Event
{
    private Entity entity;
    private Block block;
    private BlockPos blockPos;
    private LocationHelper locationHelper;
    private AxisAlignedBB boundingBox;

    public BoundingBoxEvent(Entity entity, LocationHelper locationHelper, final Block block, final BlockPos pos, final AxisAlignedBB boundingBox)
    {
        this.entity = entity;
        this.locationHelper = locationHelper;
        this.block = block;
        this.blockPos = pos;
        this.boundingBox = boundingBox;
    }

    public Block getBlock()
    {
        return this.block;
    }

    public LocationHelper getLocation()
    {
        return locationHelper;
    }

    public BlockPos getBlockPos()
    {
        return this.blockPos;
    }

    public Entity getEntity()
    {
        return entity;
    }

    public AxisAlignedBB getBoundingBox()
    {
        return this.boundingBox;
    }

    public void setBlock(final Block block)
    {
        this.block = block;
    }

    public void setBlockPos(final BlockPos blockPos)
    {
        this.blockPos = blockPos;
    }

    public void setBoundingBox(final AxisAlignedBB boundingBox)
    {
        this.boundingBox = boundingBox;
    }
}
