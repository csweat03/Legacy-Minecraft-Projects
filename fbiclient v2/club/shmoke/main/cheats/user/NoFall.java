package club.shmoke.main.cheats.user;

import club.shmoke.api.anticheat.Anticheat;
import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import club.shmoke.api.event.EventHandler;
import club.shmoke.main.events.PacketEvent;
import club.shmoke.main.events.UpdateEvent;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Cheat {

    private Property<Mode> mode = new Property<>(this, "Mode", Mode.GROUND);

    private double distance = 0;

    public NoFall() {
        super("NoFall", 0, Category.USER, "Negates Fall Damage");
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        switch (mode.getValue()) {
            case AAC:
                if (blockUtility.getBlockUnderPlayer(mc.thePlayer, distance) == Blocks.air && mc.thePlayer.motionY < -0.5)
                    distance += 1;
                else distance = 0;

                if (distance >= 1) {
                    mc.gameSettings.keyBindJump.pressed = mc.gameSettings.keyBindForward.pressed = mc.gameSettings.keyBindRight.pressed = mc.gameSettings.keyBindLeft.pressed = mc.gameSettings.keyBindBack.pressed = false;
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - (distance + 12), mc.thePlayer.posZ);
                    mc.thePlayer.fallDistance = 0;
                }
                break;
            case KOHI:
                if (mc.thePlayer.fallDistance > 3.0f) {
                    mc.thePlayer.isInWeb = true;
                    mc.thePlayer.moveEntity(0.0, -1.0, 0.0);
                    mc.thePlayer.fallDistance = 0;
                }
                if (mc.thePlayer.fallDistance > 0.3)
                    for (int i = 0; i < 30; ++i)
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                break;
        }
    }

    @EventHandler
    public void onPacket(PacketEvent packetEvent) {
        if (mc.theWorld == null || packetEvent.getType() != PacketEvent.Type.SEND) return;

        if (mode.getValue() == Mode.GROUND) {
            if (mc.thePlayer.ticksExisted > 30 && packetEvent.getPacket() instanceof C03PacketPlayer && mc.thePlayer.fallDistance >= 3 && (mc.thePlayer.fallDistance <= 20 || Anticheat.findAnticheat() != Anticheat.WATCHDOG)) {
                C03PacketPlayer c03 = (C03PacketPlayer) packetEvent.getPacket();
                c03.onGround = true;
            }
        }
    }

    private enum Mode {
        GROUND, AAC, KOHI
    }

}
