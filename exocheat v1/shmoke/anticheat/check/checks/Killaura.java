package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
public class Killaura extends Check {

    private Map<Player, Map.Entry<Integer, Long>> lastAttack = new HashMap<>();

    public Killaura() {
        super("Killaura");
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player) || event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || getPlayer() != event.getDamager())
            return;
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && getPlayer() == event.getDamager()) {
            Player player = (Player) event.getDamager();
            if (lastAttack.containsKey(player)) {
                Integer id = lastAttack.get(player).getKey();
                Long time = lastAttack.get(player).getValue();
                if (id != event.getEntity().getEntityId() && System.currentTimeMillis() - time < 100L)
                    alert.flagPlayer(this, 1, AlertHelper.AlertType.POSSIBLE, playerMoveEvent);
                lastAttack.remove(player);
            } else {
                lastAttack.put(player, new AbstractMap.SimpleEntry<>(event.getEntity().getEntityId(), System.currentTimeMillis()));
            }
        }
    }

    public void onMove() {
    }

    public void onPlace() {
    }

}
