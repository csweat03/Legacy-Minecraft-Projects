package com.fbiclient.fbi.impl.cheats.motion;

import com.fbiclient.fbi.client.framework.helper.ICheats;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.render.RenderGuiEvent;

@CheatManifest(label = "Sprint", description = "Sprints for you", category = Category.MOTION)
public class Sprint extends Cheat {

    @Register
    public void handle(RenderGuiEvent event) {
        sprint();
    }

    private void sprint() {
        if (!mc.thePlayer.onGround || mc.thePlayer.isSprinting() == canSprint()
                || mc.thePlayer.isCollidedHorizontally) {
            return;
        }
        mc.gameSettings.keyBindSprint.setKeyDown(canSprint());
    }

    private boolean canSprint() {
        return mc.thePlayer.getFoodStats().getFoodLevel() > 6 && mc.thePlayer.onGround && mc.thePlayer.moveForward > 0.0f && (ICheats.SCAFFOLD.mode != Scaffold.Mode.AAC || !ICheats.SCAFFOLD.getState());
    }

}
