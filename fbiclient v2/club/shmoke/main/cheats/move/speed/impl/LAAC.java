package club.shmoke.main.cheats.move.speed.impl;

import club.shmoke.main.cheats.move.speed.SpeedHelper;
import net.minecraft.util.MathHelper;

public class LAAC extends SpeedHelper {

    private int jumps = 0;

    @Override
    public void onUpdate() {
        if (mc.thePlayer.getSpeed() <= 0.1) jumps = 0;

        mc.gameSettings.keyBindLeft.pressed = mc.gameSettings.keyBindRight.pressed = false;
        double gay = 0.165;
        if (mc.thePlayer.motionY <= -0.25 && mc.thePlayer.fallDistance <= 1) mc.thePlayer.motionY = -0.4;
        mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + (
                jumps == 1 ? (mc.thePlayer.motionY > -gay ? 0.001 : 0.00075) :
                        jumps == 2 ? (mc.thePlayer.motionY > -gay ? 0.002 : 0.0015) :
                                jumps == 3 ? (mc.thePlayer.motionY > -gay ? 0.0045 : 0.0035) :
                                        jumps >= 4 ? (mc.thePlayer.motionY > -gay ? 0.0055 : 0.00475) : 0));
        if (mc.thePlayer.onGround)
            jump(0.405);
    }

    @Override
    protected void jump(double motion) {
        super.jump(motion);
        jumps++;
    }
}
