package club.shmoke.main.exocheat.checks;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.check.CheckInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
@CheckInfo(label = "NoFall", alertsTillBan = 2)
public class NoFall extends Check {

    private Map<Player, Integer> fallDistance = new HashMap<>(), fallDist2 = new HashMap<>(), spoof = new HashMap<>();
    private Map<Player, Long> bullyMap = new HashMap<>();

    protected void onMove(Player player, PlayerMoveEvent event) {
        double yDiff = event.getTo().getY() - event.getFrom().getY();
        if (yDiff >= 0 || onGround() || isOnClimable() || player.isDead()) {
            fallDistance.remove(player);
            fallDist2.remove(player);
            return;
        }

        if (getLocation().subtract(0, fallDistance.computeIfAbsent(player, p -> 0) + 1, 0).getBlock().getType() == Material.AIR)
            fallDistance.put(player, fallDistance.get(player) + 1);
        if (getLocation().subtract(0, fallDist2.computeIfAbsent(player, p -> 0) + 1, 0).getBlock().getType() == Material.AIR)
            fallDist2.put(player, fallDist2.get(player) + 1);

        if (yDiff < fallDist2.getOrDefault(player, 0) * -0.5 && fallDist2.getOrDefault(player, 0) > 3) {
            bullyMap.put(player, System.currentTimeMillis());
        }

        if (bullyMap.containsKey(player)) {
            if (System.currentTimeMillis() < bullyMap.get(player) + 400) {
                fallDist2.put(player, 1);
                AlertManager.GET.notifyStaff(this, event, "FastFall", player);
                event.setTo(event.getFrom());
            } else {
                bullyMap.remove(player);
            }
        }

        if (onGround() != player.isOnGround()) {
            spoof.put(player, spoof.getOrDefault(player, 0) + 1);
        } else {
            spoof.remove(player);
        }

        if (spoof.getOrDefault(player, 0) > 1) {
            AlertManager.GET.notifyStaff(this, event, "Spoof (Claimed to be on-ground whistle midair)", player);
            spoof.remove(player);
        }

        if (getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR && fallDistance.get(player) > 3) {
            player.setFallDistance(0);
            double hurt = 0.5 * fallDistance.get(player);
            if (hurt > 20) hurt = 20;
            if (hurt < 0) hurt = 0;
            player.setHealth(player.getHealth() - hurt);
            fallDistance.remove(player);
        }
        if (isDebug())
            for (Player p : Bukkit.getOnlinePlayers()) message(p, fallDistance.getOrDefault(player, 0) + " " + yDiff);
    }
}
