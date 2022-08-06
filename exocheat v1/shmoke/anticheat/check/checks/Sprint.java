package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Sprint extends Check {

    public Sprint() {
        super("Sprint");
    }

    public void onMove() {
        if (getPlayer().isSprinting()) {
            if (getPlayer().getFoodLevel() < 6)
                alert.flagPlayer(this, 2, AlertHelper.AlertType.BLATANT, playerMoveEvent);
        }
    }

    public void onPlace(){}
}
