package club.shmoke.anticheat.check.checks.speed;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.TimeHelper;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeedMotionY extends Check {
    private Map<Player, Integer> airTicks = new HashMap<>();
    private List<Player> attackList = new ArrayList<>();
    private TimeHelper attackDelay = new TimeHelper();

    public SpeedMotionY() {
        super("Speed [MotionY]");
    }

    public void onMove() {
        if (isOnLiquid() || isInLiquid(0.2) || isOnStair() || isColliding(true) || isColliding(false) || isOnClimable())
            return;
        if (!onGround())
            airTicks.put(getPlayer(), airTicks.getOrDefault(getPlayer(), 0) + 1);
        else
            airTicks.remove(getPlayer());
        double yDiff = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY();
        int airTicks = this.airTicks.getOrDefault(getPlayer(), 0);
        if (airTicks > 35) return;
        if (yDiff != 0)
            Anticheat.anticheat.alertManager.debug(this, getPlayer().getName() + " " + airTicks + " " + Anticheat.anticheat.math.round(yDiff, 2) + " " + getSpeed(playerMoveEvent));

        boolean highHop = yDiff >= maxY() + 1E-323 && yDiff < 0.5;
        boolean lowHop = yDiff > 0 && yDiff < maxXZ() - 0.000001 && airTicks == 0;

        if (highHop || lowHop)
            alert.flagPlayer(this, 5, AlertHelper.AlertType.BLATANT, playerMoveEvent);
//        if (((yDiff >= maxY() + 1.0E-323 && yDiff < 0.5)
//                || (yDiff > 0 && yDiff < maxY() - 0.000001 && airTicks == 0)
//                || ((airTicks >= 1 && airTicks <= 4) && yDiff < 0 && yDiff > -0.25 && Anticheat.anticheat.math.round(yDiff, 2) != -0.08)) && !attackList.contains(getPlayer()))
//            alert.flagPlayer(this, 5, AlertHelper.AlertType.BLATANT, playerMoveEvent);

        if (attackList.contains(getPlayer())) {
            if (attackDelay.hasReached(550L)) attackList.remove(getPlayer());
        } else
            attackDelay.reset();
    }

    public void onPlace() {
    }

    @EventHandler
    public void onInteract(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        attackDelay.reset();
        if (e.getCause() == DamageCause.ENTITY_ATTACK)
            attackList.add((Player) e.getEntity());
    }
}
