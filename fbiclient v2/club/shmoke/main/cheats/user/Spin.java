package club.shmoke.main.cheats.user;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.event.EventHandler;
import club.shmoke.main.events.UpdateEvent;

import java.util.Random;

public class Spin extends Cheat {

    public Spin() {
        super("Spin", 0, Category.MISC, "Automatically spin");
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (event.getType() != UpdateEvent.Type.PRE) return;

        mc.thePlayer.rotationYaw = new Random().nextInt(1000);
        mc.thePlayer.rotationPitch = new Random().nextInt(1000);

    }
}
