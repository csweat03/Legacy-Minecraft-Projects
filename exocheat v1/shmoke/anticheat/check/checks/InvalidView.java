package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;

public class InvalidView extends Check {

    public InvalidView() {
        super("InvalidView");
    }

    public void onMove() {
        if (getPitch() > 90F || getPitch() < -90F)
            alert.flagPlayer(this, 1, AlertHelper.AlertType.BLATANT, playerMoveEvent);
    }

    public void onPlace() {}
}
