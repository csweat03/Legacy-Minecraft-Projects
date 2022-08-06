package club.shmoke.anticheat.check.checks.flight;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
public class FlightHorizontal extends Check {

    private Map<Player, Integer> airTicks = new HashMap<>();

    public FlightHorizontal() {
        super("Flight [Horizontal]");
    }

    public void onMove() {
        double yDiff = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY();
        if (!onGround())
            airTicks.put(getPlayer(), airTicks.getOrDefault(getPlayer(), 0) + 1);
        else
            airTicks.remove(getPlayer());

        int airTicks = this.airTicks.getOrDefault(getPlayer(), 0);

        if (!isFullyAirborne()) return;

        //if (airTicks > 30 && yDiff > 0)
            //alert.flagPlayer(this, 3, AlertHelper.AlertType.BLATANT, playerMoveEvent);

        //if (yDiff >= 1.5 && yDiff <= 2)
        // alert.flagPlayer(this, 3, AlertHelper.AlertType.BLATANT, playerMoveEvent);

        if (yDiff == 0) {
            boolean checkone = airTicks > 8;
            boolean checktwo = getLocation().add(0, -0.5, 0).getBlock().getType() == Material.AIR;
            if ((checkone || checktwo) && getSpeed(playerMoveEvent) >= 0.7)
                alert.flagPlayer(this, 3, AlertHelper.AlertType.BLATANT, playerMoveEvent);
        }

        //boolean checkone = airTicks > 20 && yDiff > -0.05 && yDiff < 0.42;
        //boolean checktwo = airTicks > 25 && yDiff > 0.42 && getLocation().subtract(0, 2.5, 0).getBlock().getType() == Material.AIR;
        boolean checkthree = airTicks > 5 && yDiff > -0.05 && yDiff < 0.05 && getLocation().subtract(0, 0.5, 0).getBlock().getType() == Material.AIR;
        if (checkthree)
            alert.flagPlayer(this, 3, AlertHelper.AlertType.BLATANT, playerMoveEvent);
    }

    public void onPlace() {
    }

    private boolean isFullyAirborne() {
        return getBoundIsAir(getLocation())
                && getBoundIsAir(getLocation().add(0.8, -0.25, 0))
                && getBoundIsAir(getLocation().add(-0.8, -0.25, 0))
                && getBoundIsAir(getLocation().add(0, -0.25, 0.8))
                && getBoundIsAir(getLocation().add(0, -0.25, -0.8))

                && getBoundIsAir(getLocation().add(0.8, -0.25, 0.8))
                && getBoundIsAir(getLocation().add(-0.8, -0.25, -0.8))
                && getBoundIsAir(getLocation().add(-0.8, -0.25, 0.8))
                && getBoundIsAir(getLocation().add(0.8, -0.25, -0.8))
                ;
    }

    private boolean getBoundIsAir(Location loc) {
        return isAir(loc, 1, 0, 0)
                && isAir(loc, -1, 0, 0)
                && isAir(loc, 0, 0, 1)
                && isAir(loc, 0, 0, -1)

                && isAir(loc, 1, 0, 1)
                && isAir(loc, -1, 0, -1)
                && isAir(loc, -1, 0, 1)
                && isAir(loc, 1, 0, -1);
    }

    private boolean isAir(Location loc, double x, double y, double z) {
        return loc.add(x, y, z).getBlock().getType() == Material.AIR;
    }
}
