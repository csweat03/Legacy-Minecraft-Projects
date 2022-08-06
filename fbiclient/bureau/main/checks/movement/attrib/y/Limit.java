package com.fbiclient.bureau.main.checks.movement.attrib.y;

import com.fbiclient.bureau.Bureau;
import com.fbiclient.bureau.api.alert.AlertManagment;
import com.fbiclient.bureau.main.checks.movement.InvalidMovement;
import com.fbiclient.bureau.main.checks.movement.attrib.IMAttribute;
import com.fbiclient.bureau.main.players.CustomPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;

public class Limit extends IMAttribute {

    @Override
    public void onExecution(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        CustomPlayer custom = getCPlayer(event);

        Location to = event.getTo();
        Location from = event.getFrom();

        float limit = 0.5f;
        double yDiff = to.getY() - from.getY();
        if (player.getAllowFlight() || player.isInsideVehicle() || custom.getLogic(event).onGround() || yDiff <= -0.3) {
            InvalidMovement.overallTravel.remove(player);
            return;
        }

        if (yDiff <= 0)
            return;

        InvalidMovement.overallTravel.put(player, InvalidMovement.overallTravel.getOrDefault(player, 0.0) + yDiff);

        if (custom.getLogic(event).onGround())
            limit = 0.42f;

        int l = custom.getLogic(event).getPotionLevel(player, PotionEffectType.JUMP);

        if (l > 0)
            limit += 0.2 * l;

        DecimalFormat dF = new DecimalFormat("#.###");

        if (yDiff > limit)
            AlertManagment.flag(Bureau.getBureau().getCheckRegistry().getContent(InvalidMovement.class), player, "Y: " + dF.format(yDiff) + " L: " + dF.format(limit));
    }
}
