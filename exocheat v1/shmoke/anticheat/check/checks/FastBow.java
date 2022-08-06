package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.AlertManager;
import club.shmoke.anticheat.helper.TimeHelper;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class FastBow extends Check {

    TimeHelper time = new TimeHelper();

    public FastBow() {
        super("FastBow");
    }

    public void onMove(){}
    public void onPlace(){}

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            double power = arrow.getVelocity().length();
            if (!time.hasReached(500) && power > 1) {
                event.setCancelled(true);
                Anticheat.anticheat.alertManager.alert(getPlayer(), this, AlertManager.CheckType.BLATANT, 5);
            }
            time.reset();
        }
    }

}
