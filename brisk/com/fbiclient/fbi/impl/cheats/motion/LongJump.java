package com.fbiclient.fbi.impl.cheats.motion;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Event;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.player.MoveEvent;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;

@CheatManifest(label = "Long Jump", description = "Jump farther", category = Category.MOTION)
public class LongJump extends Cheat {

    @Register
    private void onUpdate(UpdateMotionEvent event) {
        if (event.getType() == Event.Type.PRE) {
            if (mc.thePlayer.onGround)// && mc.thePlayer.getSpeed() <= MOTION_HELPER.getBaseMoveSpeed())
                mc.thePlayer.jump();
//            else if (mc.thePlayer.onGround) {
//                mc.thePlayer.setSpeed(MOTION_HELPER.getBaseMoveSpeed() - 0.05);
//                //toggle();
//            }
        }
    }

    @Register
    public void handleMovement(MoveEvent event) {
//                if (mc.thePlayer.motionY < 0.42f)
//                    mc.thePlayer.motionY += 0.045;
//                else {
//                    mc.thePlayer.motionY -= 0.12;
//                    mc.thePlayer.setSpeed(0.8);
//                }
//                if (!flag) {
        if (mc.thePlayer.motionY <= 0.42f)
            mc.thePlayer.motionY += 0.04;
        else
            mc.thePlayer.motionY += 0.03;
        if (!mc.thePlayer.onGround && mc.thePlayer.isMoving())
            mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 0.04);
//                    else if (mc.thePlayer.getSpeed() > MOTION_HELPER.getBaseMoveSpeed()) flag = true;
//                } else {
//                    if (mc.thePlayer.motionY <= 0.42f)
//                        mc.thePlayer.motionY += 0.05;
//                    if (!mc.thePlayer.onGround)
//                        mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 0.01);
//                    else if (mc.thePlayer.getSpeed() > MOTION_HELPER.getBaseMoveSpeed()) flag = false;
//                }
//                if (mc.thePlayer.motionY == 0.42f)
//                    mc.thePlayer.motionY += 0.12;
//                else
//                    mc.thePlayer.motionY += 0.05;
//                if (!mc.thePlayer.onGround)
//                    mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 0.04 + (Math.max(mc.thePlayer.motionY, 0.1) * 0.05));
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1;
    }

    public enum Mode {
        MINEPLEX
    }

}
