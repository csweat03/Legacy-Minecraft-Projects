package com.fbiclient.fbi.client.events.render;

import me.xx.api.event.Event;
import net.minecraft.block.Block;
/**
 * @author Kyle
 * @since 2/2/2018
 **/
public class RenderBlockEvent extends Event
{
    private int x;
    private int y;
    private int z;
    private final Block block;

    public RenderBlockEvent(final int x, final int y, final int z, final Block block) {
        this.block = block;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Block getBlock() {
        return this.block;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void setZ(final int z) {
        this.z = z;
    }

}
