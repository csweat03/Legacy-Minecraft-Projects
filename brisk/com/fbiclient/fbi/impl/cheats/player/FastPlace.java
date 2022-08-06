package com.fbiclient.fbi.impl.cheats.player;

import me.valkyrie.api.value.Val;
import me.valkyrie.api.value.types.constrain.Clamp;
import me.valkyrie.api.value.types.constrain.Increment;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Event;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;

/**
 * @author Kyle
 * @since 5/6/2018
 **/

@CheatManifest(label = "Fast Place", description = "Place blocks faster", category = Category.PLAYER)
public class FastPlace extends Cheat {

    @Val(label = "Delay")
    @Clamp(min = "0", max = "5")
    @Increment
    public int delay;

    @Register
    public void handleMotion(UpdateMotionEvent event) {
        if (event.getType() == Event.Type.PRE) {
            mc.rightClickDelayTimer = delay;
        }
    }

    public void onDisable() {
        mc.rightClickDelayTimer = 6;
    }

}
