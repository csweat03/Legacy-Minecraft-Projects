package com.fbiclient.bureau.main.checks;

import com.fbiclient.bureau.api.alert.AlertManagment;
import com.fbiclient.bureau.api.check.Check;
import com.fbiclient.bureau.api.check.annotes.CheckManifest;
import com.fbiclient.bureau.main.players.CustomPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;


@CheckManifest(label = "Sprint")
public class Sprint extends Check {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        CustomPlayer player = new CustomPlayer(event);

        if (!player.getPlayer().isSprinting()) return;

        if (player.getPlayer().getFoodLevel() < 6) {
            AlertManagment.flag(this, player.getPlayer(), "Health");
        }
    }
}
