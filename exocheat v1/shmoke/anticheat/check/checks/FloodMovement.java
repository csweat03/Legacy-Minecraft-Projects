package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;

/**
 * @author Christian
 */
public class FloodMovement extends Check {

    public FloodMovement() {
        super("Flood Movement");
    }

    public void onMove() {
        double yDiff = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY();

        if (getSpeed(playerMoveEvent) > maxXZ() + 1.5 || yDiff >= maxY() + 0.5)
            Anticheat.anticheat.alertManager.debug(this, getPlayer(), "you would be banned");
        //Anticheat.anticheat.alertManager.banPlayer(this, getPlayer());
    }

    public void onPlace() {
    }

}
