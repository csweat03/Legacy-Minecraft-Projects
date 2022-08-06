package club.shmoke.main.cheats.move.speed.impl;

import club.shmoke.main.cheats.move.speed.SpeedHelper;

public class SlowHop extends SpeedHelper {

    @Override
    public void onUpdate() {
        mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() + 0.0015);
        jump(0.42);
    }
}
