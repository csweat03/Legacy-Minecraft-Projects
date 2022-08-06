package club.shmoke.main.cheats.view;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.event.EventHandler;
import club.shmoke.main.events.UpdateEvent;

public class AntiEffects extends Cheat {

    private float gamma = 0;

    public AntiEffects() {
        super("Anti Effects", 0, Category.VIEW, "Removes darkness, negatives potion effects, etc");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        gamma = mc.gameSettings.gammaSetting;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = gamma;
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        mc.thePlayer.getActivePotionEffects().forEach(potionEffect -> {
            switch (potionEffect.getPotionID()) {
                case 9:
                case 15:
                    mc.thePlayer.removePotionEffect(potionEffect.getPotionID());
                    break;
            }
        });

        mc.gameSettings.gammaSetting = 100;
    }

}
