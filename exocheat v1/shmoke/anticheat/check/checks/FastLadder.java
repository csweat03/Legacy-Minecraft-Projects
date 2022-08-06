package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class FastLadder extends Check {

    Map<Player, Integer> ticks = new HashMap<>();
    List<Player> onLadder = new ArrayList<>();

    public FastLadder() {
        super("FastLadder");
    }

    public void onMove() {
        double yDiff = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY();
        Player p = getPlayer();
        if (isOnClimable())
            ticks.put(p, ticks.getOrDefault(p, 0) + 1);
        else
            ticks.remove(p);

        if (yDiff < 0.09) {
            ticks.remove(p);
            onLadder.remove(p);
            return;
        }
        /**
         * Adds the player to a list of users to be checked for fastladder..
         */
        if (ticks.computeIfAbsent(p, player -> 0) < 5 && getLocation().subtract(0, 0.1, 0).getBlock().getType() != Material.AIR)
            onLadder.add(p);
        /**
         * Minor Check
         * yDiff on ladders shouldn't be above 0.12 after their 4 tick.
         */
        if (ticks.computeIfAbsent(p, player -> 0) > 4 && onLadder.contains(p) && yDiff > 0.12)
            alert.flagPlayer(this, 2, AlertHelper.AlertType.BLATANT, playerMoveEvent);
 
        else if (!onLadder.contains(p) && ticks.computeIfAbsent(p, player -> 0) > 0 && yDiff > 0.42)
            alert.flagPlayer(this, 2, AlertHelper.AlertType.BLATANT, playerMoveEvent);

        /**
         * Blatant Check
         * yDiff on ladders should be at most 0.42 under any conditions.
         */
        if (ticks.computeIfAbsent(p, player -> 0) >= 0 && isOnClimable() && yDiff > 0.5)
            alert.flagPlayer(this, 2, AlertHelper.AlertType.BLATANT, playerMoveEvent);
    }

    public void onPlace(){}
}
