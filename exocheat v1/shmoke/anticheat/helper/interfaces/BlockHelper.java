package club.shmoke.anticheat.helper.interfaces;

import club.shmoke.anticheat.helper.IBukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockHelper implements IBukkit {

    public boolean isOnStair() {
        double dist = 0.2;
        return subtract(0, dist, 0) == Material.WOOD_STAIRS
                || subtract(0, dist, 0) == Material.COBBLESTONE_STAIRS
                || subtract(0, dist, 0) == Material.BRICK_STAIRS
                || subtract(0, dist, 0) == Material.SMOOTH_STAIRS
                || subtract(0, dist, 0) == Material.NETHER_BRICK_STAIRS
                || subtract(0, dist, 0) == Material.SANDSTONE_STAIRS
                || subtract(0, dist, 0) == Material.SPRUCE_WOOD_STAIRS
                || subtract(0, dist, 0) == Material.BIRCH_WOOD_STAIRS
                || subtract(0, dist, 0) == Material.JUNGLE_WOOD_STAIRS
                || subtract(0, dist, 0) == Material.QUARTZ_STAIRS
                || subtract(0, dist, 0) == Material.ACACIA_STAIRS
                || subtract(0, dist, 0) == Material.DARK_OAK_STAIRS
                || subtract(0, dist, 0) == Material.RED_SANDSTONE_STAIRS;
    }

    public boolean isOnLiquid() {
        return (subtract(0, 0.1, 0) == Material.STATIONARY_WATER
                || subtract(0, 0.1, 0) == Material.WATER
                || subtract(0, 0.1, 0) == Material.LAVA
                || subtract(0, 0.1, 0) == Material.STATIONARY_LAVA) && add(0, 0.1, 0) == Material.AIR;
    }

    public boolean isOnClimable() {
        return isColliding(1, Material.LADDER)
                || isColliding(1, Material.VINE) || isColliding(0, Material.LADDER)
                || isColliding(0, Material.VINE);
    }

    public boolean isColliding(double dist, Material material) {
        return getLocation().add(dist, -0.25, 0).getBlock().getType() == material
                || getLocation().subtract(dist, -0.25, 0).getBlock().getType() == material
                || getLocation().add(0, -0.25, dist).getBlock().getType() == material
                || getLocation().subtract(0, -0.25, dist).getBlock().getType() == material;
    }

    public boolean isColliding(double dist) {
        return (add(dist, 0,0) != Material.AIR
                || add(-dist, 0,0) != Material.AIR
                || add(0, 0,dist) != Material.AIR
                || add(0, 0,-dist) != Material.AIR
                || add(dist, 0,dist) != Material.AIR
                || add(-dist, 0,dist) != Material.AIR
                || add(-dist, 0,-dist) != Material.AIR
                || add(dist, 0,-dist) != Material.AIR)
                && !isOnClimable();
    }

    public Material getBlockUnderPlayer(double dist) {
        return getLocation().subtract(0,dist,0).getBlock().getType();
    }

    public boolean isInLiquid(double distance) {
        return add(0, distance, 0) == Material.STATIONARY_WATER
                || add(0, distance, 0) == Material.STATIONARY_LAVA
                || add(0, distance, 0) == Material.LAVA
                || add(0, distance, 0) == Material.WATER;
    }
    public boolean isInGround(double distance) {
        // Need to check for webs, etc.
        return add(0, distance, 0) != Material.AIR;
    }

    public boolean onGround() {
        Block block = getLocation().subtract(0.0, 0.01, 0.0).getBlock();
        return !block.getType().equals(Material.AIR);
    }

    private Material add(double x, double y, double z) {
        return getLocation().add(x, y, z).getBlock().getType();
    }

    private Material subtract(double x, double y, double z) {
        return getLocation().subtract(x, y, z).getBlock().getType();
    }
}
