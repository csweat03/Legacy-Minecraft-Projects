package club.shmoke.main.exocheat.checks;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.check.CheckInfo;
import club.shmoke.main.api.helper.Math;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
@CheckInfo(label = "Flight", silent = false)
public class Flight extends Check {

    /* Documentation is cute, but its a fair amount of work. */

    /**
     * The {@code solidTicks} value is used to locate the ticks in
     * {@code Integer} that the player has been a similar Y-Level.
     * <p>
     * The {@code verticalTicks} value is used to locate the ticks in
     * {@code Integer} that the player has been at an increasing Y-Level.
     * <p>
     * The {@code airTicks} value is used to locate the ticks in
     * {@code Integer} that the airBorne variable declared in the
     * {@docRoot BukkitAPI} is set to true.
     */
    private final Map<Player, Integer>
            solidTicks = new HashMap<>(),
            verticalTicks = new HashMap<>(),
            airTicks = new HashMap<>(),
            glideTicks = new HashMap<>(),
            verticalLocation = new HashMap<>(),
            glideLocation = new HashMap<>();

    private final Map<Player, Location>
            /*
             * The players last location before they were airborne.
             */
            horizontalLocation = new HashMap<>(),
            timeLocation = new HashMap<>();

    private double yDiff;

    protected void onMove(Player player, PlayerMoveEvent event) {

        yDiff = event.getTo().getY() - event.getFrom().getY();

        solid(player, event);
        ascension(player, event);
        time(player, event);
        glide(player, event);

    }

    private void glide(Player player, PlayerMoveEvent event) {
        if (onGround() || isOnClimable() || isInLiquid(0) || isOnLiquid())
            return;
        boolean flag = false;

        if (yDiff > -0.8 && yDiff < 0) glideTicks.put(player, glideTicks.getOrDefault(player, 0) + 1);
        else if (glideTicks.getOrDefault(player, 0) > 0)
            glideTicks.put(player, glideTicks.getOrDefault(player, 1) - 1);

        int ticks = glideTicks.getOrDefault(player, 0);

        if (ticks > 20) flag = true;

        if (getLocation().subtract(0, glideLocation.computeIfAbsent(player, p -> 0) + 1, 0).getBlock().getType() == Material.AIR)
            glideLocation.put(player, glideLocation.get(player) + 1);

        if (getLocation().subtract(0, glideLocation.computeIfAbsent(player, p -> 0) + 1, 0).getBlock().getType() != Material.AIR)
            glideLocation.put(player, glideLocation.get(player) - 1);

        if (player.getLocation().getY() >= glideLocation.get(player) - 3) flag = false;

        if (flag) {
            AlertManager.GET.notifyStaffSilently(this, event, "Glide", player);
            player.teleport(player.getLocation().subtract(0, glideLocation.get(player), 0));
            glideTicks.remove(player);
        }
    }

    private void time(Player player, PlayerMoveEvent event) {

        if (onGround()) {
            timeLocation.remove(player, player.getLocation());
            timeLocation.put(player, player.getLocation());
        }

        if (onGround())
            airTicks.put(player, -60);
        else if (!onGround())
            airTicks.put(player, airTicks.getOrDefault(player, 0) + 1);

        int air = airTicks.getOrDefault(player, 0);

        if ((air > 50 * (isStrict ? 1 : isNormal ? 2 : isLenient ? 5 : 10) && yDiff >= 0) || (air > 300 * (isStrict ? 1 : isNormal ? 2 : isLenient ? 5 : 10) && yDiff >= -5 + (isStrict ? 0 : isNormal ? 2 : isLenient ? 4.5 : 10))) {
            for (int i = 0; i < 5; i++)
                AlertManager.GET.notifyStaffSilently(this, event, "Time (Stayed Airborne for more than 50 ticks)", player);
            player.teleport(timeLocation.get(player));
        }
    }

    private void ascension(Player player, PlayerMoveEvent event) {
        if (onGround() || isOnClimable() || isInLiquid(0) || isOnLiquid())
            return;
        boolean flag = false;

        if (yDiff > 0) verticalTicks.put(player, verticalTicks.getOrDefault(player, 0) + 1);
        else if (verticalTicks.getOrDefault(player, 0) > 0)
            verticalTicks.put(player, verticalTicks.getOrDefault(player, 1) - 1);

        if (yDiff >= 0.5) flag = true;

        int ticks = verticalTicks.getOrDefault(player, 0);

        if (ticks > 5) flag = true;

        if (getLocation().subtract(0, verticalLocation.computeIfAbsent(player, p -> 0) + 1, 0).getBlock().getType() == Material.AIR)
            verticalLocation.put(player, verticalLocation.get(player) + 1);

        if (getLocation().subtract(0, verticalLocation.computeIfAbsent(player, p -> 0) + 1, 0).getBlock().getType() != Material.AIR)
            verticalLocation.put(player, verticalLocation.get(player) - 1);

        if (flag) {
            AlertManager.GET.notifyStaffSilently(this, event, "Ascension (Y-Difference: " + Math.round(yDiff, 2) + ")", player);
            player.teleport(player.getLocation().subtract(0, verticalLocation.get(player), 0));
            verticalTicks.remove(player);
        }
    }

    private void solid(Player player, PlayerMoveEvent event) {
        if (onGround())
            horizontalLocation.put(player, player.getLocation());

        if (onGround() || isOnClimable() || isInLiquid(0) || isOnLiquid() || getSpeed(event) == 0 || !isFullyAirborne() || getBlockUnderPlayer(0.3) != Material.AIR)
            return;

        solidTicks.put(player, solidTicks.getOrDefault(player, 0) + (java.lang.Math.abs(yDiff) <= 0.01 ? 1 : solidTicks.getOrDefault(player, 0) > 0 ? -1 : 0));

        int s = solidTicks.getOrDefault(player, 0);

        if (s == 0)
            horizontalLocation.remove(player);

        if (s > 10) {
            AlertManager.GET.notifyStaffSilently(this, event, "Solid", player);
            player.teleport(horizontalLocation.get(player));
            solidTicks.put(player, 1);
        }
    }

}