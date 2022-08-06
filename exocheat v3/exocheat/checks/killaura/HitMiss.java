package club.shmoke.main.exocheat.checks.killaura;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.helper.TimeHelper;
import club.shmoke.main.exocheat.ExoCheat;
import club.shmoke.main.exocheat.checks.Killaura;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
public class HitMiss {

    private static Map<Player, Integer> hits = new HashMap<>(), misses = new HashMap<>();
    private static TimeHelper hitmissTimer = new TimeHelper();
    private static Map<Player, Location> loc = new HashMap<>();

    public static void onCall(Event event) {
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;
        if (hitmissTimer.hasReached(1500)) {
            hits.clear();
            misses.clear();
            hitmissTimer.reset();
            return;
        }
        if (damageEvent.getDamager() == null || damageEvent.getEntity() == null || !(damageEvent.getDamager() instanceof Player) || !(damageEvent.getEntity() instanceof Player))
            return;
        Player player = (Player) damageEvent.getDamager(), damaged = (Player) damageEvent.getEntity();

        if (damaged.getLocation().distance(loc.getOrDefault(player, damaged.getLocation())) == 0) {
            loc.put(damaged, damaged.getLocation());
            return;
        }

        loc.put(damaged, damaged.getLocation());

        hits.put(player, hits.getOrDefault(player, 0) + 1);
        if (misses.getOrDefault(player, 0) > 0)
            misses.put(player, misses.get(player) - 1);

        int hit = hits.getOrDefault(player, 0), miss = misses.getOrDefault(player, 0);
        if (hit - miss > 10) AlertManager.GET.notifyStaff(ExoCheat.get().getCheckManager().get(Killaura.class), damageEvent, "Hit/Miss Ratio", player);
    }

    public static void onCall2(Event event) {
        PlayerAnimationEvent animationEvent = (PlayerAnimationEvent) event;
        Player player = animationEvent.getPlayer();
        if (animationEvent.getAnimationType() == PlayerAnimationType.ARM_SWING) {
            misses.put(player, misses.getOrDefault(player, 0) + 1);
        }
    }
}
