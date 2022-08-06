package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.MathHelper;
import club.shmoke.anticheat.helper.TimeHelper;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class Step extends Check {

    private TimeHelper attackDelay = new TimeHelper();
    private List<Player> attackList = new ArrayList<>();


    public Step() {
        super("Step");
    }

    public void onMove() {
        double yDiff = Anticheat.anticheat.math.round(playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY(), 10);
        if (!attackList.contains(getPlayer()) && getBlockUnderPlayer(1) != Material.CACTUS && isColliding(true) && !isOnClimable() && !isOnLiquid() && !isInLiquid(0.1) && yDiff != 0.5 && yDiff > 0 && yDiff != 0.4199999869 && yDiff != 0.3331999936 && yDiff != 0.2481359986 && yDiff != 0.1647732818 && yDiff != 0.0830778178)
            alert.flagPlayer(this, 4, AlertHelper.AlertType.BLATANT, playerMoveEvent);
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

    public void onPlace() {}
}
