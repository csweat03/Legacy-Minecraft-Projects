package club.shmoke.anticheat.listeners;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.checks.FloodMovement;
import club.shmoke.anticheat.helper.IBukkit;
import club.shmoke.anticheat.helper.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class MainListener implements Listener, IBukkit {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Anticheat.anticheat.players.put(p.getUniqueId(), new User(p));
        if (Anticheat.anticheat.getConfig().contains(event.getPlayer().getUniqueId().toString()))
            event.getPlayer().kickPlayer(Anticheat.anticheat.banReason);
        setPlayer(event);
        event.setJoinMessage("");
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        Anticheat.anticheat.players.remove(p.getUniqueId());
        event.setQuitMessage("");
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (event.getLeaveMessage() == "Flying is not enabled on this server" || event.getReason() == "Flying is not enabled on this server") {
            Anticheat.anticheat.alertManager.debug(Anticheat.anticheat.checkManager.get(FloodMovement.class), event.getPlayer(), "kicked for flight");
            event.setCancelled(true);
        }
        if (event.getLeaveMessage() == "disconnect.spam" || event.getReason() == "disconnect.spam") {
            Anticheat.anticheat.alertManager.debug(Anticheat.anticheat.checkManager.get(FloodMovement.class), event.getPlayer(), "kicked for spam");
            event.setCancelled(true);
        }
        //Anticheat.anticheat.alertManager.banPlayer(Anticheat.anticheat.checkManager.get(FloodMovement.class), event.getPlayer());
    }
}
