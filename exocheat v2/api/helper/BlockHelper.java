package club.shmoke.main.api.helper;

import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author Christian
 */
public enum BlockHelper {

    GET;

    public boolean isOn(Player player, Material[] mats) {
        for (Material mat : mats)
            for (double x = -1.5; x < 3; x += 0.1)
                for (double y = 0; y < 1.5; y += 0.1)
                    for (double z = -1.5; z < 3; z += 0.1)
                        if (player.getLocation().subtract(x, y, z).getBlock().getType() == mat)
                            return true;
        return false;
    }

    public boolean isOnIce(Player player) {
        return isOn(player, new Material[] {Material.PACKED_ICE, Material.ICE});
    }

    public boolean isOnSnow(Player player) {
        return isOn(player, new Material[] {Material.SNOW});
    }

    public boolean isCollidingHorizontally(Player player) {
        for (double y = 0.1; y < 1.9; y += 0.1) {
            if (player.getLocation().add(1, y, 0).getBlock().getType() != Material.AIR ||
                    player.getLocation().add(-1, y, 0).getBlock().getType() != Material.AIR ||
                    player.getLocation().add(0, y, 1).getBlock().getType() != Material.AIR ||
                    player.getLocation().add(0, y, -1).getBlock().getType() != Material.AIR) {
                return true;
            }
        }
        return false;
    }

    public boolean isHittingHead(Player player) {
        for (double x = -0.45; x < 0.45; x += 0.1)
            for (double y = 0; y < 1; y += 0.1)
                for (double z = -0.45; z < 0.45; z += 0.1)
                    if (player.getLocation().add(x, 2 + y, z).getBlock().getType() != Material.AIR)
                        return true;
        return false;
    }

}
