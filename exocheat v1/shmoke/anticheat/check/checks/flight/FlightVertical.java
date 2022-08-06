package club.shmoke.anticheat.check.checks.flight;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
public class FlightVertical extends Check {

    private Map<Player, Integer> fast = new HashMap<>(), slow = new HashMap<>();

    public FlightVertical() {
        super("Flight [Vertical]");
    }

    public void onMove() {
        double yDiff = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY();

        if (!onGround() && yDiff >= 0.5)
            fast.put(getPlayer(), fast.getOrDefault(getPlayer(), 0) + 1);
        else {
            fast.put(getPlayer(), fast.getOrDefault(getPlayer(), 0) - 1);
        }
        if (!onGround() && yDiff > 0 && yDiff < 0.42)
            slow.put(getPlayer(), slow.getOrDefault(getPlayer(), 0) + 1);
        else
            slow.put(getPlayer(), slow.getOrDefault(getPlayer(), 0) - 1);

        if ((fast.getOrDefault(getPlayer(), 0) > 1 || slow.getOrDefault(getPlayer(), 0) > 8) && !onGround())
            alert.flagPlayer(this, 4, AlertHelper.AlertType.BLATANT, playerMoveEvent);
    }

    public void onPlace() {}

}
