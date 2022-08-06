package com.fbiclient.fbi.impl.cheats.misc;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.game.TickEvent;

/**
 * @author Kyle
 * @since 5/7/2018
 **/
@CheatManifest(label = "Auto Respawn", category = Category.MISC, description = "Auto respawns when you die")
public class AutoRespawn extends Cheat {

    @Register
    public void handleTick(TickEvent event) {
        if (mc.thePlayer.isDead) {
            mc.thePlayer.respawnPlayer();
        }
    }

}
