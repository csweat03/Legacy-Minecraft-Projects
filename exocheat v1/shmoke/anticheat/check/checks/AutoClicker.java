package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.TimeHelper;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class AutoClicker extends Check {
    TimeHelper time = new TimeHelper();
    private Map<Player, Integer> clicks = new HashMap<>();

    public AutoClicker() {
        super("AutoClicker [CPS]");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!isActive())
            return;
        if (getPlayer() != event.getPlayer()) return;
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK)
            return;
        if (isDebug()) {
            Anticheat.anticheat.alertManager.debug(this, getPlayer(), clicks.getOrDefault(getPlayer(), 0) + " " + time.getTimePassed());
        }
        if (time.hasReached(1000L)) {
            clicks.remove(getPlayer());
            time.reset();
        } else {
            clicks.put(getPlayer(), clicks.getOrDefault(getPlayer(), 0) + 1);
            int clicks = this.clicks.getOrDefault(getPlayer(), 0);
            if (clicks >= 14)
                alert.flagPlayer(this, 2, clicks < 17 ? AlertHelper.AlertType.POSSIBLE : AlertHelper.AlertType.BLATANT, event);
        }
    }

    public void onMove() {
    }

    public void onPlace() {
    }
}
