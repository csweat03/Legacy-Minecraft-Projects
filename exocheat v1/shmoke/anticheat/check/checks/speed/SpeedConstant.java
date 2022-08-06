package club.shmoke.anticheat.check.checks.speed;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 */
public class SpeedConstant extends Check {

    private Map<Player, Double> speed = new HashMap<>();
    private Map<Player, Integer> airTicks = new HashMap<>();

    public SpeedConstant() {
        super ("Speed [Constant]");
    }

    public void onMove(){
        if (isOnLiquid() || isInLiquid(0.2) || isOnStair() || isColliding(true) || isColliding(false) || isOnClimable())
            return;
        if (!onGround())
            airTicks.put(getPlayer(), airTicks.getOrDefault(getPlayer(), 0) + 1);
        else
            airTicks.remove(getPlayer());
        int airTicks = this.airTicks.getOrDefault(getPlayer(), 0);

        if (airTicks == 2) speed.put(getPlayer(), getSpeed(playerMoveEvent));

        double oldSpeed = speed.getOrDefault(getPlayer(), 0.0);
        if (airTicks >= 3 && airTicks <= 8 && getSpeed(playerMoveEvent) >= oldSpeed - 0.000025 && getSpeed(playerMoveEvent) > 0.15 && getSpeed(playerMoveEvent) <= oldSpeed + 0.000025)
            alert.flagPlayer(this, 3, AlertHelper.AlertType.BLATANT, playerMoveEvent);


    }

    public void onPlace(){}

}
