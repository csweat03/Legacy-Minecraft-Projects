package club.shmoke.client.util.math;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class LocationHelper
{
    private double x, y, z;
    private float yaw, pitch;

    public LocationHelper(double x, double y, double z, float yaw, float pitch)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public LocationHelper(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
    }

    public LocationHelper(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
    }

    public LocationHelper add(int x, int y, int z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public LocationHelper add(double x, double y, double z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public LocationHelper subtract(int x, int y, int z)
    {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public LocationHelper subtract(double x, double y, double z)
    {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Block getBlock()
    {
        return Minecraft.getMinecraft().theWorld.getBlockState(this.toBlockPos()).getBlock();
    }

    public double getX()
    {
        return x;
    }

    public LocationHelper setX(double x)
    {
        this.x = x;
        return this;
    }

    public double getY()
    {
        return y;
    }

    public LocationHelper setY(double y)
    {
        this.y = y;
        return this;
    }

    public double getZ()
    {
        return z;
    }

    public LocationHelper setZ(double z)
    {
        this.z = z;
        return this;
    }

    public float getYaw()
    {
        return yaw;
    }

    public LocationHelper setYaw(float yaw)
    {
        this.yaw = yaw;
        return this;
    }

    public float getPitch()
    {
        return pitch;
    }

    public LocationHelper setPitch(float pitch)
    {
        this.pitch = pitch;
        return this;
    }

    public static LocationHelper fromBlockPos(BlockPos blockPos)
    {
        return new LocationHelper(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public BlockPos toBlockPos()
    {
        return new BlockPos(getX(), getY(), getZ());
    }
}
