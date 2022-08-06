package club.shmoke.client.cheats.environment;

import net.minecraft.network.play.client.C03PacketPlayer;
import club.shmoke.client.Client;
import club.shmoke.api.anticheat.Anticheat;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.cheats.movement.SpawnRun;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.client.events.other.SendPacketEvent;
import club.shmoke.api.event.interfaces.EventListener;

public class NoFall extends Cheat implements IHelper {
    public NoFall() {
        super("NoFall", Type.ENVIRONMENT);
        this.description = "Negates Fall-Damage";
    }

    @EventListener
    public void onSend(SendPacketEvent e) {
        if (mc.theWorld == null) return;
        if (e.getPacket() instanceof C03PacketPlayer && mc.thePlayer.fallDistance >= 3F && (mc.thePlayer.fallDistance <= 20F || Client.INSTANCE.getAnticheatManager().findAnticheat() == Anticheat.GWEN) && !Client.INSTANCE.getCheatManager().get(SpawnRun.class).getState()) {
            C03PacketPlayer packet = (C03PacketPlayer) e.getPacket();
            packet.onGround = true;
        }
    }
}
