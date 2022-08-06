package com.fbiclient.fbi.impl.cheats.motion.scaffold;

import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.impl.cheats.motion.Scaffold;
import me.xx.api.event.Event;
import me.xx.utility.Stopwatch;

public class Hypixel implements IHelper {

    private Stopwatch timer = new Stopwatch();

    public void onEnable() {
        timer.reset();
    }

    public void onDisable() {
        mc.rightClickDelayTimer = 6;
    }

    public void onUpdate(UpdateMotionEvent event) {
        if (mc.gameSettings.keyBindSneak.isKeyDown()) return;

        mc.rightClickDelayTimer = 4;

        changeRotations(event);

        if (event.getType() == Event.Type.POST)
            if (Scaffold.canPlace())
                Scaffold.placeGenericBlock();
    }

    public void changeRotations(UpdateMotionEvent event) {
        if (event.getType() != Event.Type.PRE) return;

        float[] rot = Scaffold.getGenericAngles();

        event.setYaw(rot[0]);
        event.setPitch(rot[1]);
    }
}