package club.shmoke.anticheat.helper.interfaces;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.helper.IBukkit;
import club.shmoke.anticheat.helper.MathHelper;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MoveHelper implements IBukkit {

    public void moveLocation(double x, double y, double z) {
        teleport(getX() + x, getY() + y, getZ() + z);
    }

    public void teleport(double x, double y, double z) {
        getPlayer().teleport(new Location(getWorld(), x, y, z));
    }

    public Location getLocation() {
        return getPlayer().getLocation();
    }

    public double getX() {
        return getLocation().getX();
    }

    public double getY() {
        return getLocation().getY();
    }

    public double getZ() {
        return getLocation().getZ();
    }

    public double getYaw() {
        return getLocation().getYaw();
    }

    public double getPitch() {
        return getLocation().getPitch();
    }

    public double getSpeed(PlayerMoveEvent e) {
        return Anticheat.anticheat.math.sqrt(Anticheat.anticheat.math.square(e.getTo().getX() - e.getFrom().getX()) + Anticheat.anticheat.math.square(e.getTo().getZ() - e.getFrom().getZ()));
    }

    public double maxXZ() {
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

    public double maxY() {
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
}
