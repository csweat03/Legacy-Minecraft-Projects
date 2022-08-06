package club.shmoke.main.cheats.move.flight;

import club.shmoke.api.utility.Utility;

public class Vanilla extends Utility {

    public void onUpdate() {
        mc.thePlayer.capabilities.isFlying = true;
        mc.thePlayer.capabilities.setFlySpeed(0.5f);
    }

    public void onDisable() {
        mc.thePlayer.capabilities.isFlying = false;
        mc.thePlayer.capabilities.setFlySpeed(0.1f);
    }

}
