package club.shmoke.api.utility.utilities;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

/**
 * @author Christian
 */
public class LocationUtility {

    private double x, y, z;
    private float yaw, pitch;

    public LocationUtility(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public LocationUtility(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
    }

    public LocationUtility(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
    }

    public static LocationUtility fromBlockPos(BlockPos blockPos) {
        return new LocationUtility(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public LocationUtility add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public LocationUtility add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public LocationUtility subtract(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public LocationUtility subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Block getBlock() {
        return Minecraft.getMinecraft().theWorld.getBlockState(this.toBlockPos()).getBlock();
    }

    public double getX() {
        return x;
    }

    public LocationUtility setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public LocationUtility setY(double y) {
        this.y = y;
        return this;
    }

    public double getZ() {
        return z;
    }

    public LocationUtility setZ(double z) {
        this.z = z;
        return this;
    }

    public float getYaw() {
        return yaw;
    }

    public LocationUtility setYaw(float yaw) {
        this.yaw = yaw;
        return this;
    }

    public float getPitch() {
        return pitch;
    }

    public LocationUtility setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public BlockPos toBlockPos() {
        return new BlockPos(getX(), getY(), getZ());
    }

}
