package com.fbiclient.bureau.main.checks.movement;

import com.fbiclient.bureau.api.check.Check;
import com.fbiclient.bureau.api.check.annotes.CheckManifest;
import com.fbiclient.bureau.api.check.annotes.Prevention;
import com.fbiclient.bureau.main.checks.movement.attrib.IMAttributeLoader;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

@Prevention
@CheckManifest(label = "Invalid Movement")
public class InvalidMovement extends Check {

    public static Map<Player, Integer> thresholdA = new HashMap<>();
    public static Map<Player, Double> overallTravel = new HashMap<>();
    public static Map<Player, Location> previousSafeLocation = new HashMap<>();

    public InvalidMovement() {
        new IMAttributeLoader().loadAttributes();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        new IMAttributeLoader().executeAttributes(event);
    }

}
