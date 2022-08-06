package club.shmoke.main.api.helper;

import club.shmoke.main.api.check.Check;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.Material.AIR;

/**
 * @author Christian
 */
public interface IBukkit {

    default Player getPlayer() {
        return Check.player;
    }

    default Location getLocation() {
        return getPlayer().getLocation();
    }

    default double getXZCap() {
        double base = 0.2873;
        if (getPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
            PotionEffect potion = getPlayer().getActivePotionEffects().iterator().next();
            if (potion.getType().equals(PotionEffectType.SPEED)) {
                int amplifier = potion.getAmplifier();
                base *= 1.0 + (onGround() ? 0.2 : 0.1) * (amplifier + 1);
            }
        }
        return base;
    }

    default double getYCap() {
        double base = 0.42;
        if (getPlayer().hasPotionEffect(PotionEffectType.JUMP)) {
            PotionEffect potion = getPlayer().getActivePotionEffects().iterator().next();
            if (potion.getType().equals(PotionEffectType.JUMP)) {
                int amplifier = potion.getAmplifier();
                base *= 1.0 + (0.1 * (amplifier + 1));
            }
        }
        return base;
    }

    default double getYaw() {
        return getLocation().getYaw();
    }

    default double getPitch() {
        return getLocation().getPitch();
    }

    default double getSpeed(PlayerMoveEvent event) {
        return Math.sqrt(Math.square(event.getTo().getX() - event.getFrom().getX()) + Math.square(event.getTo().getZ() - event.getFrom().getZ()));
    }

    default double getDistanceToEntity(Player player, Player target) {
        double x1 = player.getLocation().getX(), z1 = player.getLocation().getZ();
        double x2 = target.getLocation().getX(), z2 = target.getLocation().getZ();
        double x = x1 - x2, z = z1 - z2;
        return Math.sqrt(Math.square(x) + Math.square(z));
    }

    default boolean onGround() {
        Material m = getLocation().subtract(0, 0.01, 0).getBlock().getType();
        return m != AIR;
    }

    default boolean isOnStair() {
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

    default boolean isOnLiquid() {
        return (subtract(0, 0.1, 0) == Material.STATIONARY_WATER
                || subtract(0, 0.1, 0) == Material.WATER
                || subtract(0, 0.1, 0) == Material.LAVA
                || subtract(0, 0.1, 0) == Material.STATIONARY_LAVA) && add(0, 0.1, 0) == Material.AIR;
    }

    default boolean isOnClimable() {

        return isColliding(0, Material.LADDER)
                || isColliding(0, Material.VINE);

//        return isColliding(1, Material.LADDER)
//                || isColliding(1, Material.VINE) || isColliding(0, Material.LADDER)
//                || isColliding(0, Material.VINE);
    }

    default boolean isColliding(double dist, Material material) {
        return getLocation().add(dist, 0.5, 0).getBlock().getType() == material
                || getLocation().subtract(dist, -0.5, 0).getBlock().getType() == material
                || getLocation().add(0, 0.5, dist).getBlock().getType() == material
                || getLocation().subtract(0, -0.5, dist).getBlock().getType() == material;
    }

    default boolean isColliding(double dist) {
        return (add(dist, 0, 0) != Material.AIR
                || add(-dist, 0, 0) != Material.AIR
                || add(0, 0, dist) != Material.AIR
                || add(0, 0, -dist) != Material.AIR
                || add(dist, 0, dist) != Material.AIR
                || add(-dist, 0, dist) != Material.AIR
                || add(-dist, 0, -dist) != Material.AIR
                || add(dist, 0, -dist) != Material.AIR)
                && !isOnClimable();
    }

    default Material getBlockUnderPlayer(double dist) {
        return getLocation().subtract(0, dist, 0).getBlock().getType();
    }

    default Material getBlockAbovePlayer(double dist) {
        return getLocation().subtract(0, -2 - dist, 0).getBlock().getType();
    }


    default boolean isInLiquid(double distance) {
        return add(0, distance, 0) == Material.STATIONARY_WATER
                || add(0, distance, 0) == Material.STATIONARY_LAVA
                || add(0, distance, 0) == Material.LAVA
                || add(0, distance, 0) == Material.WATER;
    }

    default Material add(double x, double y, double z) {
        return getLocation().add(x, y, z).getBlock().getType();
    }

    default Material subtract(double x, double y, double z) {
        return getLocation().subtract(x, y, z).getBlock().getType();
    }

    default boolean isFullyAirborne() {
        return getBoundIsAir(getLocation())
                && getBoundIsAir(getLocation().add(0.8, -0.25, 0))
                && getBoundIsAir(getLocation().add(-0.8, -0.25, 0))
                && getBoundIsAir(getLocation().add(0, -0.25, 0.8))
                && getBoundIsAir(getLocation().add(0, -0.25, -0.8))

                && getBoundIsAir(getLocation().add(0.8, -0.25, 0.8))
                && getBoundIsAir(getLocation().add(-0.8, -0.25, -0.8))
                && getBoundIsAir(getLocation().add(-0.8, -0.25, 0.8))
                && getBoundIsAir(getLocation().add(0.8, -0.25, -0.8))
                ;
    }

    default boolean getBoundIsAir(Location loc) {
        return isAir(loc, 1, 0, 0)
                && isAir(loc, -1, 0, 0)
                && isAir(loc, 0, 0, 1)
                && isAir(loc, 0, 0, -1)

                && isAir(loc, 1, 0, 1)
                && isAir(loc, -1, 0, -1)
                && isAir(loc, -1, 0, 1)
                && isAir(loc, 1, 0, -1);
    }

    default boolean isAir(Location loc, double x, double y, double z) {
        return loc.add(x, y, z).getBlock().getType() == Material.AIR;
    }
}
