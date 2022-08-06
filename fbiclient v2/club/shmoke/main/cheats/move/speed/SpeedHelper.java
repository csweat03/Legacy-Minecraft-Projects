package club.shmoke.main.cheats.move.speed;

import club.shmoke.api.utility.Utility;
import club.shmoke.main.cheats.move.Speed;
import net.minecraft.util.MathHelper;

public abstract class SpeedHelper extends Utility {

    public abstract void onUpdate();

    public Speed.Mode getMode() {
        return Speed.Mode.valueOf(this.getClass().getSimpleName().toUpperCase());
    }

    protected void jump(double motion) {

        if (!mc.thePlayer.onGround || mc.gameSettings.keyBindJump.pressed) return;

        mc.thePlayer.motionY = motion;

        if (mc.thePlayer.isSprinting()) {
            float f = mc.thePlayer.rotationYaw * 0.017453292F;
            mc.thePlayer.motionX -= (double) (MathHelper.sin(f) * 0.2F);
            mc.thePlayer.motionZ += (double) (MathHelper.cos(f) * 0.2F);
        }
        mc.thePlayer.isAirBorne = true;
    }

}
