package club.shmoke.anticheat.check.checks.speed;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.TimeHelper;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class SpeedExteremity extends Check {

    private TimeHelper attackDelay = new TimeHelper();
    private List<Player> attackList = new ArrayList<>();

    public SpeedExteremity() {
        super("Speed [Extremity]");
    }

    public void onMove() {
        if (!attackList.contains(getPlayer()) && getSpeed(playerMoveEvent) > maxXZ() + 0.35)
            alert.flagPlayer(this, 3, AlertHelper.AlertType.BLATANT, playerMoveEvent);


        if (attackList.contains(getPlayer())) {
            if (attackDelay.hasReached(550L)) attackList.remove(getPlayer());
        } else
            attackDelay.reset();
    }

    @EventHandler
    public void onInteract(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        attackDelay.reset();
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
            attackList.add((Player) e.getEntity());
    }

    public void onPlace() {
    }
}
