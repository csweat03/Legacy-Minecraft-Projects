package club.shmoke.main.cheats.move.speed.impl;

import club.shmoke.main.cheats.move.speed.SpeedHelper;
import net.minecraft.block.BlockAir;
import org.lwjgl.input.Keyboard;

public class KohiGround extends SpeedHelper {

    @Override
    public void onUpdate() {
        if (blockUtility.getBlockUnderPlayer(mc.thePlayer, 0.5) instanceof BlockAir || mc.gameSettings.keyBindJump.pressed) {
            mc.thePlayer.setSpeed(0);
            return;
        }

        mc.thePlayer.setSpeed(mc.thePlayer.getSpeed());

        mc.timer.timerSpeed = 0.8f;

        if (mc.thePlayer.onGround) mc.thePlayer.motionY = 0.07;
        else if (mc.thePlayer.motionY < -0.01) {
            mc.thePlayer.motionY = 0;
            mc.thePlayer.setSpeed(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ? 4.5 : 0.9);
        }

    }
}
