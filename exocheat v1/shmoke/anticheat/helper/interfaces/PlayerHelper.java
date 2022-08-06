package club.shmoke.anticheat.helper.interfaces;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.helper.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerHelper {

    private Player player = null;
    
    public void setPlayer(Event event) {
        switch (event.getEventName()) {
            case "PlayerMoveEvent":
                player = ((PlayerMoveEvent) event).getPlayer();
                break;
            case "PlayerJoinEvent":
                player = ((PlayerJoinEvent) event).getPlayer();
                break;
            case "PlayerKickEvent":
                player = ((PlayerKickEvent) event).getPlayer();
                break;
            case "BlockPlaceEvent":
                player = ((BlockPlaceEvent) event).getPlayer();
                break;
            case "PlayerInteractEvent":
                player = ((PlayerInteractEvent) event).getPlayer();
                break;
            case "InventoryOpenEvent":
                Player playr = (Player) ((InventoryOpenEvent) event).getPlayer();
                player = playr;
                break;
            case "InventoryCloseEvent":
                Player play = (Player) ((InventoryCloseEvent) event).getPlayer();
                player = play;
                break;
            case "InventoryClickEvent":
                Player pla = (Player) ((InventoryClickEvent) event).getWhoClicked();
                player = pla;
                break;
        }
        Anticheat.anticheat.players.put(player.getUniqueId(), new User(player));

    }

    public Player getPlayer() {
        return player;
    }
}
