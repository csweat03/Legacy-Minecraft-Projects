package club.shmoke.main.exocheat.checks;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.check.CheckInfo;
import club.shmoke.main.api.helper.TimeHelper;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author Christian
 */

@CheckInfo(label = "FastUse")
public class FastUse extends Check {

    private TimeHelper time = new TimeHelper();

    protected void onMove(Player player, PlayerMoveEvent event) {
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            double power = arrow.getVelocity().length();
            if (!time.hasReached(500) && power > 1) {
                if (!isSilent())
                    event.setCancelled(true);
                AlertManager.GET.notifyStaff(this, event, "Bow", getPlayer());
            }
            time.reset();
        }
    }
}
