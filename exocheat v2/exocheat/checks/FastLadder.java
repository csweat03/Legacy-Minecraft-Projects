package club.shmoke.main.exocheat.checks;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.check.CheckInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Christian
 */
@CheckInfo(label = "FastLadder", silent = false)
public class FastLadder extends Check {

    private Map<Player, Integer> ticks = new HashMap<>();
    private List<Player> onLadder = new ArrayList<>();

    @Override
    protected void onMove(Player player, PlayerMoveEvent event) {
        double yDiff = event.getTo().getY() - event.getFrom().getY();
        Player p = getPlayer();
        if (isOnClimable())
            ticks.put(p, ticks.getOrDefault(p, 0) + 1);
        else
            ticks.remove(p);

        if (yDiff < 0) {
            if (ticks.getOrDefault(player, 0) > 0)
                ticks.put(player, ticks.getOrDefault(player, 1) - 1);
            onLadder.remove(p);
            return;
        }
        /*
         * Adds the player to a list of users to be checked for fastladder..
         */
        if (ticks.computeIfAbsent(p, pl -> 0) < 5 && getLocation().subtract(0, 0.1, 0).getBlock().getType() != Material.AIR)
            onLadder.add(p);
        /*
         * Minor Check
         * yDiff on ladders shouldn't be above 0.12 after their 4 tick.
         */
        if (ticks.computeIfAbsent(p, pl -> 0) > 8 && onLadder.contains(p) && yDiff > 0.12)
            AlertManager.GET.notifyStaff(this, event, player);

        else if (!onLadder.contains(p) && ticks.computeIfAbsent(p, pl -> 0) > 0 && yDiff > 0.425)
            AlertManager.GET.notifyStaff(this, event, player);
        /*
         * Blatant Check
         * yDiff on ladders should be at most 0.45 under any conditions.
         */
        if (ticks.computeIfAbsent(p, pl -> 0) >= 0 && isOnClimable() && yDiff > 0.45)
            AlertManager.GET.notifyStaff(this, event, player);
    }
}
