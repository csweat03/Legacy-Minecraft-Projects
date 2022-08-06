package club.shmoke.anticheat.check.checks.jesus;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;

public class JesusA extends Check {

    int waterTicks = 0;
    int waterTicks2 = 0;

    public JesusA() {
        super("Jesus [Walk]");
    }

    public void onMove() {
        double yDiff = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY();
        if (isOnLiquid()) {
            waterTicks = 5;
            waterTicks2++;
        } else if (waterTicks > 0) {
            waterTicks--;
            waterTicks2 = 0;
        } else waterTicks2 = 0;

        if (((yDiff > -0.01 && yDiff < 0.01) || (yDiff > 0.1)) && waterTicks > 0 && waterTicks2 > 3) {
            alert.flagPlayer(this, 5, AlertHelper.AlertType.BLATANT, playerMoveEvent);
        }
    }

    public void onPlace() {}
}
