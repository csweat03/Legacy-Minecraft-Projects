package com.fbiclient.fbi.impl.cheats.motion.scaffold;

import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.impl.cheats.motion.Scaffold;
import me.xx.utility.Stopwatch;

/**
 * @author Christian
 */
public class AAC implements IHelper {

    private Stopwatch timer = new Stopwatch();

    public void onEnable() {
        timer.reset();
    }

    public void onDisable() {
        mc.rightClickDelayTimer = 6;
    }

    public void onUpdate(UpdateMotionEvent event) {
        mc.gameSettings.keyBindSneak.setKeyDown(false);
        mc.gameSettings.keyBindSprint.setKeyDown(false);
        mc.thePlayer.setSprinting(false);

        if (mc.thePlayer.isSprinting()) return;

        //changeRotations(event);
        //mc.rightClickDelayTimer = 4;
        //if (mc.thePlayer.isMoving())
        //mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + (mc.thePlayer.ticksExisted % 2 == 0 ? 0.01 : 0.0005));
        //if (event.getType() == Event.Type.POST)
            //if (Scaffold.isAirBorne() && Scaffold.canPlace())
                //Scaffold.placeGenericBlock();
    }

    public void changeRotations(UpdateMotionEvent event) {
        float[] rot = Scaffold.getGenericAngles();
        event.setYaw(rot[0]);
        event.setPitch(rot[1]);
    }

}
