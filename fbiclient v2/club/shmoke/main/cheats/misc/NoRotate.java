package club.shmoke.main.cheats.misc;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.event.EventHandler;
import club.shmoke.main.events.PacketEvent;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Cheat {

    public NoRotate() {
        super("NoRotate", 0, Category.MISC, "Prevents server from setting your rotations");
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.theWorld != null && event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
            packet.setYaw(mc.thePlayer.rotationYaw);
            packet.setPitch(mc.thePlayer.rotationPitch);
        }
    }

}
