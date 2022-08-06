package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.AlertManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class Reach extends Check {

    HashMap<Player, Double> reach = new HashMap<>();

    public Reach() {
        super("Reach");
    }

    public void onMove() {
    }

    public void onPlace() {
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
        if (!isActive() || event.getDamager() != getPlayer()) return;
        Player player = (Player) event.getDamager();
        Player damaged = (Player) event.getEntity();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        reach.put(player, 3.52);
        if (reach.containsKey(player)) {
            if (player.isSprinting())
                reach.put(player, reach.get(player) + 0.1);
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.SPEED))
                    reach.put(player, reach.get(player) + (0.2 * (effect.getAmplifier() + 1)));
            }

            if (getLocation().distance(damaged.getLocation()) > reach.get(player)) {
                if (!isSilent())
                    event.setCancelled(true);
                Anticheat.anticheat.alertManager.alert(player, this, AlertManager.CheckType.PROBABLE, 2);
            }
        }
    }
}
