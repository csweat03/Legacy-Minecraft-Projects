package club.shmoke.main.cheats.move.speed.impl;

import club.shmoke.main.cheats.move.Speed;
import club.shmoke.main.cheats.move.speed.SpeedHelper;

public class Mineplex extends SpeedHelper {

    @Override
    public void onUpdate() {
        jump(0.42);
        mc.thePlayer.setSpeed(0.44);
    }
}
