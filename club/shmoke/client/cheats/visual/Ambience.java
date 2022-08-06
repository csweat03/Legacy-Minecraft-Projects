package club.shmoke.client.cheats.visual;

import net.minecraft.network.play.server.S03PacketTimeUpdate;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.client.events.other.RecievePacketEvent;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.api.event.interfaces.EventListener;

public class Ambience extends Cheat {

    Property<Integer> time = new Property(this, "Time", 16000, 0, 16000, 500);
    Property<Boolean> max_gamma = new Property<>(this, "Max Gamma", false);
    private float gamma = 0;

    public Ambience() {
        super("Ambience", Type.VISUAL);
    }

    @EventListener
    public void onRecieve(RecievePacketEvent e) {
        if (!max_gamma.getValue()) {
            if (e.getPacket() instanceof S03PacketTimeUpdate)
                e.setCancelled(true);
        }
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent e) {
        if (!max_gamma.getValue())
            mc.theWorld.setWorldTime(time.getValue());
        else
            mc.gameSettings.gammaSetting = 50;
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        gamma = mc.gameSettings.gammaSetting;
    }
    @Override
    public void onDisable()
    {
        super.onDisable();
        mc.gameSettings.gammaSetting = gamma;
    }
}
