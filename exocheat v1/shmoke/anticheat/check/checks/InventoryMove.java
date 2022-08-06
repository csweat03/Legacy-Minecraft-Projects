package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
public class InventoryMove extends Check {

    Map<Player, Location> locationMap = new HashMap<>();

    public InventoryMove() {
        super("Inventory Move");
    }

    public void onPlace() {
    }

    public void onMove() {
        if (!locationMap.containsKey(getPlayer())) return;
        double yDiff = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY();
        if (getSpeed(playerMoveEvent) >= 0.1 && yDiff > -0.1) {
            if (!isSilent())
                getPlayer().closeInventory();
            alert.flagPlayer(this, 2, AlertHelper.AlertType.POSSIBLE, playerMoveEvent);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (isActive() && event.getPlayer() instanceof Player) {
            setPlayer(event);
            locationMap.put(getPlayer(), getPlayer().getLocation());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (isActive() && event.getWhoClicked() instanceof Player) {
            setPlayer(event);
            locationMap.put(getPlayer(), getPlayer().getLocation());
        }
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        if (isActive() && event.getPlayer() instanceof Player && getPlayer() == event.getPlayer())
            if (locationMap.containsKey(getPlayer())) locationMap.remove(getPlayer());
    }

}
