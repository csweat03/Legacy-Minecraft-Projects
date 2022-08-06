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
public class FlightGlide extends Check {

    private Map<Player, Integer> glideTicks = new HashMap<>();

    public FlightGlide() {
        super("Flight [Glide]");
    }

    public void onMove() {
        double yDiff = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY();
        if (!onGround() && yDiff >= -0.7 && yDiff < 0.0)
            glideTicks.put(getPlayer(), glideTicks.getOrDefault(getPlayer(), 0) + 1);
        else
            glideTicks.remove(getPlayer());
        if (glideTicks.getOrDefault(getPlayer(), 0) >= 15)
            alert.flagPlayer(Anticheat.anticheat.checkManager.get(FlightGlide.class), 3, AlertHelper.AlertType.BLATANT, playerMoveEvent);
    }

    public void onPlace() {}
}
