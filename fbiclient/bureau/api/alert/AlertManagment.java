package com.fbiclient.bureau.api.alert;

import com.fbiclient.bureau.BureauData;
import com.fbiclient.bureau.api.check.Check;
import com.fbiclient.bureau.api.check.annotes.Experimental;
import com.fbiclient.bureau.main.events.update.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class AlertManagment implements Listener {

    private static Map<Player, Integer> violationLevel = new HashMap<>();
    private static Map<Player, Location> prev = new HashMap<>();

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (event.getSpeed() == UpdateEvent.Speed.FAST)
            for (Player player : Bukkit.getOnlinePlayers()) {
                prev.put(player, player.getLocation());
                if (violationLevel.getOrDefault(player, 1) >= 1)
                    violationLevel.put(player, violationLevel.getOrDefault(player, 1) - 1);
            }

    }

    public static void flag(Check check, Player violator, String message) {
        final int PRIMARY = 6;

        if (!check.isSilent())
            violator.teleport(prev.get(violator));

        for (Player player : Bukkit.getOnlinePlayers()) {
            violationLevel.put(violator, violationLevel.getOrDefault(violator, 0) + 1);

            StringBuilder msg = new StringBuilder(String.format("\2478[\247%s\247l%s\247r\2478] \247%s%s \2477", PRIMARY, BureauData.getName(), PRIMARY, violator.getName()));

            boolean isExp = check.getClass().isAnnotationPresent(Experimental.class);
            boolean hasMessage = !message.equalsIgnoreCase("");

            msg.
                    append(isExp ? "might have failed" : "has failed").
                    append(" \2476").
                    append(check.getLabel()).
                    append(" \2477[\2476").
                    append(violationLevel.get(violator)).
                    append("\2477] ").
                    append(isExp ? " \247c\247l[Experimental]\247r" : "").
                    append(" ").
                    append(String.format(hasMessage ? "(%s)" : "", message));

            player.sendMessage(msg.toString());
        }
    }
}
