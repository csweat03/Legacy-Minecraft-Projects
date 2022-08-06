package club.shmoke.main.exocheat.checks.killaura;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.exocheat.ExoCheat;
import club.shmoke.main.exocheat.checks.Killaura;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian
 */
public class Swing {

    private static List<Player> swing = new ArrayList<>();

    public static void onCall(Event event) {
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;
        if (!(damageEvent.getDamager() instanceof Player)) return;
        Player p = (Player) damageEvent.getDamager();
        if (!swing.contains(p))
            AlertManager.GET.notifyStaff(ExoCheat.get().getCheckManager().get(Killaura.class), event, "Swing", p);
        swing.remove(p);
    }

    public static void onCall2(Event event) {
        PlayerAnimationEvent animationEvent = (PlayerAnimationEvent) event;
        Player player = animationEvent.getPlayer();
        if (animationEvent.getAnimationType() == PlayerAnimationType.ARM_SWING) {
            if (!swing.contains(player)) swing.add(player);
        }
    }
}
