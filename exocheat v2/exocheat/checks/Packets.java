package club.shmoke.main.exocheat.checks;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.check.CheckInfo;
import club.shmoke.main.exocheat.ExoCheat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
@SuppressWarnings("ALL")
@CheckInfo(label = "Packets")
public class Packets extends Check {

    private final Map<Player, Integer> packet = new HashMap<>();

    public Packets() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(ExoCheat.get(), () -> {
            for (Player player : Bukkit.getOnlinePlayers())
                remove(player);
        }, 1, 1);
    }

    protected void onMove(Player player, PlayerMoveEvent event) {
        put(player);

        int packets = packet.getOrDefault(player, 0);

        if (packets > 12) {
            packet.remove(player);
            AlertManager.GET.notifyStaff(this, event, "Timer", player);
        }

        if (isDebug()) message(getPlayer(), packets);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        packet.remove(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        packet.remove(event.getPlayer());
    }

    private void remove(Player p) {
        if (packet.getOrDefault(p, 0) <= 1)
            packet.remove(p);
        else
            packet.put(p, packet.get(p) - 1);
    }

    private void put(Player p) {
        packet.put(p, packet.getOrDefault(p, 0) + 1);
    }
}
