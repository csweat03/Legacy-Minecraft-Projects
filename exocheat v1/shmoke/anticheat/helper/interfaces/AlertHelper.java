package club.shmoke.anticheat.helper.interfaces;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.AlertManager;
import club.shmoke.anticheat.helper.IBukkit;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class AlertHelper implements IBukkit {

    public void flagPlayer(Check c, Integer violation, AlertType type, Event event) {
        if (!c.isSilent()) {
            switch (event.getEventName()) {
                case "PlayerMoveEvent":
                    ((PlayerMoveEvent) event).setTo(((PlayerMoveEvent) event).getFrom());
                    break;
                case "PlayerKickEvent":
                    ((PlayerKickEvent) event).setCancelled(true);
                    break;
                case "BlockPlaceEvent":
                    ((BlockPlaceEvent) event).setCancelled(true);
                    break;
                case "ProjectileLaunchEvent":
                    ((ProjectileLaunchEvent) event).setCancelled(true);
                    break;
                case "PlayerInteractEvent":
                    ((PlayerInteractEvent) event).setCancelled(true);
                    break;
                case "PlayerInteractEntityEvent":
                    ((PlayerInteractEntityEvent) event).setCancelled(true);
                    break;
            }
        }
        Anticheat.anticheat.alertManager.alert(getPlayer(), c, type == AlertType.BLATANT ? AlertManager.CheckType.BLATANT : AlertManager.CheckType.PROBABLE, violation);
    }

    public enum AlertType {
        BLATANT, POSSIBLE
    }
}
