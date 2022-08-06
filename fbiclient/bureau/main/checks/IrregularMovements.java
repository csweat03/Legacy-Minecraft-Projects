package com.fbiclient.bureau.main.checks;

import club.shmoke.main.utility.Timer;
import com.fbiclient.bureau.api.alert.AlertManagment;
import com.fbiclient.bureau.api.check.Check;
import com.fbiclient.bureau.api.check.annotes.Experimental;
import com.fbiclient.bureau.api.check.annotes.CheckManifest;
import com.fbiclient.bureau.main.players.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

@Experimental
@CheckManifest(label = "Irregular Movements")
public class IrregularMovements extends Check {

    private Map<Player, Integer> yourProllyCheatingButOkay = new HashMap<>();
    private Timer timer = new Timer();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        CustomPlayer cPlayer = new CustomPlayer(event);
        double yDiff = event.getTo().getY() - event.getFrom().getY();

        if (player.getAllowFlight()) {
            yourProllyCheatingButOkay.remove(player);
            return;
        }

        /* Solid Flight */
        if (yDiff >= -0.001 && yDiff <= 0.001 && !cPlayer.getLogic(event).onGround()) {
            yourProllyCheatingButOkay.put(player, yourProllyCheatingButOkay.getOrDefault(player, 0) + 1);

            if (yourProllyCheatingButOkay.getOrDefault(player, 0) >= 5) {
                AlertManagment.flag(this, player, String.format("Y-Difference %s", yDiff));
                yourProllyCheatingButOkay.remove(player);
            }
        } else if (yourProllyCheatingButOkay.getOrDefault(player, 0) >= 2) {
            yourProllyCheatingButOkay.put(player, yourProllyCheatingButOkay.getOrDefault(player, 0) - 2);
        }

        if (player.getAllowFlight() || !cPlayer.getBlockData(player.getLocation().subtract(0, cPlayer.getLogic(event).onGround() ? 0.5 : 1.5, 0).getBlock()).isSolid()) {
            timer.reset();
            return;
        }

        /* Some Experimental gonna mess up stuff */
        if (cPlayer.getLogic(event).onGround()) timer.reset();

        int abCap = 575;

        if (yDiff >= -0.5 && timer.getLength() >= abCap) {
            AlertManagment.flag(this, player, String.format("AirBorne Cap: %s Airborne Time: %s", abCap, (timer.getLength() > 1000 ? "1000+" : timer.getLength())));
        }

        //Logger.write(timer.getLength() + "", Logger.Level.INFO);

        // calling all retards
        //if ()

    }

}
