package club.shmoke.main.exocheat.listeners;

import club.shmoke.main.exocheat.events.UpdateEvent;
import club.shmoke.main.api.listener.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.*;

/**
 * @author Christian
 */
public class MainUpdater extends Listener {

    /**
     * Disgusting but works..
     */

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Bukkit.getPluginManager().callEvent(new UpdateEvent());
    }

    @EventHandler
    public void onHeldItem(PlayerItemHeldEvent event) {
        Bukkit.getPluginManager().callEvent(new UpdateEvent());
    }

    @EventHandler
    public void onInt1(PlayerInteractEntityEvent event) {
        Bukkit.getPluginManager().callEvent(new UpdateEvent());
    }

    @EventHandler
    public void onAnimation(PlayerAnimationEvent event) {
        Bukkit.getPluginManager().callEvent(new UpdateEvent());
    }

    @EventHandler
    public void onInt2(PlayerInteractAtEntityEvent event) {
        Bukkit.getPluginManager().callEvent(new UpdateEvent());
    }

    @EventHandler
    public void onInt3(PlayerInteractEvent event) {
        Bukkit.getPluginManager().callEvent(new UpdateEvent());
    }

    @EventHandler
    public void onPlayer(PlayerEvent event) {
        Bukkit.getPluginManager().callEvent(new UpdateEvent());
    }
}
