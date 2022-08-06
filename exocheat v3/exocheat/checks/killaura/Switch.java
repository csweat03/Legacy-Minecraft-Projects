package club.shmoke.main.exocheat.checks.killaura;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.exocheat.ExoCheat;
import club.shmoke.main.exocheat.checks.Killaura;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
public class Switch {

    private static Map<Player, Map.Entry<Integer, Long>> lastAttack = new HashMap<>();

    public static void onCall(Event event) {
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;
        if (damageEvent.getDamager() instanceof Player && damageEvent.getEntity() instanceof Player && damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            Player player = (Player) damageEvent.getDamager();
            if (lastAttack.containsKey(player)) {
                Integer id = lastAttack.get(player).getKey();
                Long time = lastAttack.get(player).getValue();
                if (id != damageEvent.getEntity().getEntityId() && System.currentTimeMillis() - time < 50)
                    AlertManager.GET.notifyStaff(ExoCheat.get().getCheckManager().get(Killaura.class), event, "Switch", player);
                lastAttack.remove(player);
            } else
                lastAttack.put(player, new AbstractMap.SimpleEntry<>(damageEvent.getEntity().getEntityId(), System.currentTimeMillis()));
        }
    }
}
