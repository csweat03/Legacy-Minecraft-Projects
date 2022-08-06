package club.shmoke.anticheat.check.checks.speed;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SpeedOnGround extends Check {

    private Map<Player, Integer> groundTicks = new HashMap<>();


    public SpeedOnGround() {
        super("Speed [OnGround]");
    }

    public void onMove() {
        double yDiff = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY();
        if (onGround() && yDiff >= 0 && yDiff <= 0.001)
            groundTicks.put(getPlayer(), groundTicks.getOrDefault(getPlayer(), 0) + 1);
        else
            groundTicks.remove(getPlayer());

        if (((groundTicks.getOrDefault(getPlayer(), 0) > 1 && getSpeed(playerMoveEvent) > maxXZ() + 0.1)
                || (groundTicks.getOrDefault(getPlayer(), 0) > 5 && getSpeed(playerMoveEvent) > maxXZ() + 0.01)) && onGround())
            alert.flagPlayer(this, 1, AlertHelper.AlertType.BLATANT, playerMoveEvent);
    }

    public void onPlace() {
    }
}

