package com.fbiclient.bureau.main.checks.movement.attrib.y;

import com.fbiclient.bureau.Bureau;
import com.fbiclient.bureau.api.alert.AlertManagment;
import com.fbiclient.bureau.main.checks.movement.InvalidMovement;
import com.fbiclient.bureau.main.checks.movement.attrib.IMAttribute;
import com.fbiclient.bureau.main.players.CustomPlayer;
import com.fbiclient.utility.Logger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.text.DecimalFormat;

public class Solid extends IMAttribute {

    @Override
    public void onExecution(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        CustomPlayer custom = new CustomPlayer(event);

        double yDiff = Math.abs(event.getTo().getY() - event.getFrom().getY());

        if (player.getAllowFlight() || player.isInsideVehicle() || custom.getLogic(event).onGround() || player.getLocation().clone().subtract(0, 0.01, 0).getBlock().getType() != Material.AIR) {
            InvalidMovement.thresholdA.remove(player);
            InvalidMovement.previousSafeLocation.put(player, player.getLocation());
            return;
        }

        if (yDiff <= 0.001)
            InvalidMovement.thresholdA.put(player, InvalidMovement.thresholdA.getOrDefault(player, 0) + (yDiff <= 0.000001 ? 4 : 1));
        else if (InvalidMovement.thresholdA.getOrDefault(player, 0) >= 2)
            InvalidMovement.thresholdA.put(player, InvalidMovement.thresholdA.getOrDefault(player, 0) - 2);

        if (InvalidMovement.thresholdA.getOrDefault(player, 0) >= 8) {
            DecimalFormat dF = new DecimalFormat("#.###");
            AlertManagment.flag(Bureau.getBureau().getCheckRegistry().getContent(InvalidMovement.class), player, "Y: " + dF.format(yDiff));
            double offset = Math.abs(InvalidMovement.previousSafeLocation.getOrDefault(player, player.getLocation()).getY() - player.getLocation().getY());
            Logger.write(offset + "", Logger.Level.INFO);
            if (offset >= 1)
                player.teleport(InvalidMovement.previousSafeLocation.getOrDefault(player, player.getLocation()));
            InvalidMovement.thresholdA.remove(player);
        }
    }
}
