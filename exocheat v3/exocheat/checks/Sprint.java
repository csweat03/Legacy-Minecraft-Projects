package club.shmoke.main.exocheat.checks;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.check.CheckInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author Christian
 */
@CheckInfo(label = "Sprint", ban = true, alertsTillBan = 1)
public class Sprint extends Check {

    protected void onMove(Player player, PlayerMoveEvent event) {
        if (player.getPlayer().getFoodLevel() < 6 && player.getPlayer().isSprinting())
            AlertManager.GET.notifyStaff(this, event, player.getPlayer());
    }
}
