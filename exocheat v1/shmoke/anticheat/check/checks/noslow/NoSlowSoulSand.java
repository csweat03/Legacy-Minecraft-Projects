package club.shmoke.anticheat.check.checks.noslow;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
public class NoSlowSoulSand extends Check {

    private Map<Player, Integer> slow = new HashMap<>();
    private Map<Player, Integer> slowIce = new HashMap<>();
    private Map<Player, Integer> falseFlaggeroo = new HashMap<>();

    public NoSlowSoulSand() {
        super("NoSlow [SoulSand]");
    }

    public void onMove() {
        if (getLocation().subtract(0, 0.25, 0).getBlock().getType() == Material.SOUL_SAND && (getLocation().subtract(0, 1.25, 0).getBlock().getType() == Material.ICE || getLocation().subtract(0, 1.25, 0).getBlock().getType() == Material.PACKED_ICE)) {
            slowIce.put(getPlayer(), slowIce.getOrDefault(getPlayer(), 0) + 1);
            slow.remove(getPlayer());
            falseFlaggeroo.put(getPlayer(), 5);
        } else if (getLocation().subtract(0, 0.25, 0).getBlock().getType() == Material.SOUL_SAND) {
            slow.put(getPlayer(), slow.getOrDefault(getPlayer(), 0) + 1);
            slowIce.remove(getPlayer());
            falseFlaggeroo.put(getPlayer(), 5);
        } else {
            if (falseFlaggeroo.getOrDefault(getPlayer(), 0) > 0)
                falseFlaggeroo.put(getPlayer(), falseFlaggeroo.get(getPlayer()) - 1);
            slow.remove(getPlayer());
            slowIce.remove(getPlayer());
        }
        int slow = this.slow.getOrDefault(getPlayer(), 0), slowIce = this.slowIce.getOrDefault(getPlayer(), 0);
        if ((slow > 3 && getSpeed(playerMoveEvent) > 0.17) || (slowIce > 3 && getSpeed(playerMoveEvent) > 0.12) && falseFlaggeroo.getOrDefault(getPlayer(), 0) == 5)
            alert.flagPlayer(this, 1, AlertHelper.AlertType.POSSIBLE, playerMoveEvent);
    }

    public void onPlace() {
    }

}
