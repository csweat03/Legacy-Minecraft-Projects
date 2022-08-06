package club.shmoke.client.cheats.visual;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.events.update.UpdatePlayerEvent;

public class PlayerModel extends Cheat {

    public Property<Boolean> wings = new Property<>(this, "Wings", true);
    public Property<Boolean> dab = new Property<>(this, "Dab", false);
    public Property<Boolean> real = new Property<>(this, "Realistic Movements", false);

    public Property<Integer> scale = new Property<>(this, "Scale", 100, 50, 1000, 1);
    private Property<String> wingsOptions = new Property<>(this, "Wings");

    public PlayerModel() {
        super("Player Model", Type.VISUAL);
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent event) {
        if (real.getValue()) {
            if (mc.thePlayer.isMoving() && mc.gameSettings.thirdPersonView == 0 && mc.thePlayer.onGround) {
                mc.gameSettings.viewBobbing = true;
                if (mc.thePlayer.isSprinting()) {
                    mc.thePlayer.cameraYaw = 0.137999994f / 1.1f * 2;
                } else {
                    mc.thePlayer.cameraYaw = 0.107999994f / 1.1f * 2;

                }
            }
        }
    }
}
