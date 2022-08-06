package club.shmoke.main.exocheat.checks.killaura;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.exocheat.ExoCheat;
import club.shmoke.main.exocheat.checks.Killaura;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
public class Range {

    private static final Map<Player, Double> THRESHOLD = new HashMap<>();
    private static final double REACH = 4.2;

    public static void onCall(Event event) {
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;

        if (!(damageEvent.getEntity() instanceof Player) || !(damageEvent.getDamager() instanceof Player)) return;

        Player damager = (Player) damageEvent.getDamager();

        THRESHOLD.put(damager, REACH);

        if (damager.isSprinting()) THRESHOLD.put(damager, THRESHOLD.getOrDefault(damager, REACH) + 0.1);

        for (PotionEffect effect : damager.getActivePotionEffects())
            if (effect.getType().equals(PotionEffectType.SPEED))
                THRESHOLD.put(damager, THRESHOLD.getOrDefault(damager, REACH) + (0.2 * (effect.getAmplifier() + 1)));

        if (damager.getLocation().distance((damageEvent.getEntity()).getLocation()) > THRESHOLD.getOrDefault(damager, REACH))
            AlertManager.GET.notifyStaff(ExoCheat.get().getCheckManager().get(Killaura.class), event, "Range", damager);
    }
}
