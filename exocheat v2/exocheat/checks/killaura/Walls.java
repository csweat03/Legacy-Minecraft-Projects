package club.shmoke.main.exocheat.checks.killaura;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.exocheat.ExoCheat;
import club.shmoke.main.exocheat.checks.Killaura;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author Christian
 */
public class Walls {

    public static void onCall(Event event) {
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;
        if (damageEvent.getDamager() instanceof Player && damageEvent.getEntity() instanceof Player && damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            Player attacker = (Player) damageEvent.getDamager();
            Player damaged = (Player) damageEvent.getEntity();
            if (!isHitable(damaged)) AlertManager.GET.notifyStaff(ExoCheat.get().getCheckManager().get(Killaura.class), event, "Walls", attacker);
        }
    }

    private static boolean isHitable(Player player) {
        if (bordered(Material.AIR, player))
            return false;
        return true;
    }

    private static boolean bordered(Material mat, Player player) {
        return get(player, -1, 0, 0) != mat &&
                get(player, 1, 0, 0) != mat &&
                get(player, 0, 0, -1) != mat &&
                get(player, 0, 0, 1) != mat &&

                get(player, -1, 0, -1) != mat &&
                get(player, 1, 0, 1) != mat &&
                get(player, 1, 0, -1) != mat &&
                get(player, -1, 0, 1) != mat &&

                get(player, 0, 1, 0) != mat &&
                get(player, 0, -2, 0) != mat;
    }

    private static Material get(Player player, int x, int y, int z) {
        return player.getLocation().subtract(x, y, z).getBlock().getType();
    }

}