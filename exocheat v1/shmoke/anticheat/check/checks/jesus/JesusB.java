package club.shmoke.anticheat.check.checks.jesus;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;

public class JesusB extends Check {

    int waterTicks = 0;

    public JesusB() {
        super("Jesus [Swim]");
    }

    public void onMove() {
        double yDiff = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY();
        if (!isOnLiquid() && isInLiquid(0.2)) waterTicks++; else waterTicks = 0;

        if (isInLiquid(1)) {
            if (yDiff > 0.1)
                alert.flagPlayer(this, 5, AlertHelper.AlertType.BLATANT, playerMoveEvent);
        }
        if (isInLiquid(4.5)) {
            if (yDiff < -0.14)
                alert.flagPlayer(this, 5, AlertHelper.AlertType.BLATANT, playerMoveEvent);
        }

        if (waterTicks > 15) {
            if (getSpeed(playerMoveEvent) > 0.145 || yDiff == 0)
                alert.flagPlayer(this, 5, AlertHelper.AlertType.BLATANT, playerMoveEvent);
        }
    }

    public void onPlace(){}
}
