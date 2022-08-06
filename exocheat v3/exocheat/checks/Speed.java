package club.shmoke.main.exocheat.checks;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.check.CheckInfo;
import club.shmoke.main.api.helper.BlockHelper;
import club.shmoke.main.api.helper.TimeHelper;
import club.shmoke.main.api.helper.Math;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
@CheckInfo(label = "Speed", silent = false)
public class Speed extends Check {

    private Map<Player, Integer> airTicks = new HashMap<>(), groundTicks = new HashMap<>(), graceTicks = new HashMap<>();
    private Map<Player, Long> automationTicks = new HashMap<>();
    private Map<Player, Double> oldSpeed = new HashMap<>();
    private TimeHelper timeHelper = new TimeHelper();

    protected void onMove(Player player, PlayerMoveEvent event) {
        double speedLeniency = 0.0;
        if (!timeHelper.hasReached(1000)) return;

        int grace = graceTicks.getOrDefault(player, 0);

        if (BlockHelper.GET.isOnIce(player) || BlockHelper.GET.isHittingHead(player) || BlockHelper.GET.isOnSnow(player))
            graceTicks.put(player, 30);
        else
            graceTicks.put(player, grace - 1);

        //if (!isFullyAirborne()) speedLeniency += 0.3;

        if (grace > 0) speedLeniency += 0.48;

        double yDiff = event.getTo().getY() - event.getFrom().getY();

        addData(event);

        Tiers tier = usingAirSpeed(event, speedLeniency) ?
                Tiers.AIR : usingConstantSpeed(event) ?
                Tiers.CONSTANT : usingGroundSpeed(event, speedLeniency) ?
                Tiers.GROUND : usingMotion(event) ?
                Tiers.MOTION :
                Tiers.SAFE;

        if (tier != Tiers.SAFE)
            AlertManager.GET.notifyStaff(this, event, tier.getLabel(), player);

        if (getSpeed(event) > 0 && isDebug())
            message(player, "\247cAirTicks: \2477" + airTicks.getOrDefault(player, 0) + "\247c Speed:\2477" + Math.round(getSpeed(event), 5) + "\247c MotionY:\2477" + Math.round(yDiff, 5));
    }

    @EventHandler
    public void onHurt(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        timeHelper.reset();
    }

    private void addData(PlayerMoveEvent event) {
        if (onGround()) {
            automationTicks.put(player, System.currentTimeMillis());
            if (getSpeed(event) > 0)
                groundTicks.put(player, groundTicks.getOrDefault(player, 0) + 1);
            airTicks.remove(player);
        } else {
            groundTicks.remove(player);
            airTicks.put(player, airTicks.getOrDefault(player, 0) + 1);
        }
    }

    private boolean usingAirSpeed(PlayerMoveEvent event, double leniency) {
        double[] max =
                {0.34, 0.09, 0.1, 0.08, 0.075,
                        0.065, 0.06, 0.055, 0.052,
                        0.051, 0.049, 0.048};
        for (int i = 0; i < airTicks.getOrDefault(player, 0); i++)
            if (i < 12 && getSpeed(event) > getXZCap() + max[i] + leniency) return true;
        return false;
    }

    private boolean usingMotion(PlayerMoveEvent event) {
        int grace = graceTicks.getOrDefault(event.getPlayer(), 0);
        double yDiff = event.getTo().getY() - event.getFrom().getY();
        if (yDiff < 0.5 && !isOnClimable() && !isInLiquid(0) && !isOnLiquid() && getSpeed(event) >= getXZCap() - 0.1 && grace == 0) {
            boolean flag = false;

            switch (airTicks.getOrDefault(player, 0)) {
                case 0:
                    flag = yDiff > 0.42 || yDiff < 0.419;
                    break;
                case 1:
                    flag = yDiff > 0.335 || yDiff < 0.325;
                    break;
                case 2:
                    flag = yDiff > 0.25 || yDiff < 0.24;
                    break;
                case 3:
                    flag = yDiff > 0.17 || yDiff < 0.155;
                    break;
                case 4:
                    flag = yDiff > 0.09 || yDiff < 0.075;
                    break;
                case 5:
                    flag = yDiff > 0.0025 || yDiff < -0.0025;
                    break;
            }

            if (yDiff == 0) flag = false;

            return flag;
        }
        return false;
    }

    private boolean usingGroundSpeed(PlayerMoveEvent event, double leniency) {
        double yDiff = event.getTo().getY() - event.getFrom().getY();
        return groundTicks.getOrDefault(player, 0) > 8 && onGround() && yDiff == 0 && getSpeed(event) > getXZCap() + leniency;
    }

    private boolean usingConstantSpeed(PlayerMoveEvent event) {
        int at = airTicks.getOrDefault(player, 0);
        if (at == 2) oldSpeed.put(player, getSpeed(event));
        double os = oldSpeed.getOrDefault(player, 0.0);
        double threshold = 0.0001;
        return at > 5 && getSpeed(event) > getXZCap() - 0.05 && os >= getSpeed(event) - threshold && os <= getSpeed(event) + threshold;
    }

    enum Tiers {
        AIR("AirBorne Speed"),
        MOTION("MotionY"),
        GROUND("Ground Speed"),
        CONSTANT("Constant"),
        SAFE("you shouldn't see this");

        private String label;

        Tiers(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
