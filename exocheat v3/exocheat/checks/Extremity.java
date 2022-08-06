package club.shmoke.main.exocheat.checks;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.check.CheckInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author Christian
 */
@CheckInfo(label = "Extremity", silent = false, ban = true, alertsTillBan = 1)
public class Extremity extends Check {

    protected void onMove(Player player, PlayerMoveEvent event) {
        boolean flag = false;
        if (getSpeed(event) > 4) flag = true;
        double yDiff = event.getTo().getY() - event.getFrom().getY();
        if (yDiff < -4) flag = true;
        if (yDiff > 4) flag = true;

        if (flag)
            AlertManager.GET.notifyStaff(this, event, player.getPlayer());
    }
}
