package com.fbiclient.fbi.impl.cheats.motion;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import me.valkyrie.api.value.Val;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Event;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.game.PacketEvent;
import com.fbiclient.fbi.client.events.game.TickEvent;

@CheatManifest(label = "Velocity", description = "Cancels velocity", category = Category.COMBAT)
public class Velocity extends Cheat {

    @Val(label = "Mode")
    public Mode mode = Mode.CANCEL;

    @Register
    public void handleTicking(TickEvent event) {
        setSuffix(mode.name());
    }

    @Register
    public void handle(final PacketEvent event) {
        if (event.getType() == Event.Type.INCOMING) {
            switch (mode) {
                case CANCEL:
                    if (event.getPacket() instanceof S12PacketEntityVelocity) {
                        S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
                        if (mc.theWorld.getEntityByID(packet.getEntityID()) == mc.thePlayer) {
                            event.setCancelled(true);
                        }
                    }
                    if (event.getPacket() instanceof S27PacketExplosion) {
                        event.setCancelled(true);
                    }
                    break;
                case AAC:
                    if (mc.thePlayer.hurtTime == 1 || mc.thePlayer.hurtTime == 2 || mc.thePlayer.hurtTime == 3 || mc.thePlayer.hurtTime == 4 || mc.thePlayer.hurtTime == 5 || mc.thePlayer.hurtTime == 6 || mc.thePlayer.hurtTime == 7 || mc.thePlayer.hurtTime == 8) {
                        double yaw = mc.thePlayer.rotationYawHead;
                        yaw = Math.toRadians(yaw);
                        final double dX = -Math.sin(yaw) * 0.05;
                        final double dZ = Math.cos(yaw) * 0.05;
                        mc.thePlayer.motionX = dX;
                        mc.thePlayer.motionZ = dZ;
                    }
                    break;
            }
        }
    }

    public enum Mode {
        CANCEL, AAC;
    }
}
