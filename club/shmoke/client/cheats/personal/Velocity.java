package club.shmoke.client.cheats.personal;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.client.events.other.RecievePacketEvent;
import club.shmoke.client.events.render.HurtcamEvent;
import club.shmoke.api.event.interfaces.EventListener;

public class Velocity extends Cheat {
    public Property<Mode> mode = new Property(this, "Mode", Mode.NORMAL);
    public Property<Integer> hori = new Property<>(this, "Horizontal", 0, -200, 200, 1);
    public Property<Integer> vert = new Property<>(this, "Vertical", 0, 0, 200, 1);
    private Property<Boolean> hurtcam = new Property(this, "Hurtcam", true);
    public Velocity() {
        super("Velocity", Type.PERSONAL);
        this.description = "Negates velocity.";
    }

    double beforeHurtY = 0;

    @EventListener
    public void hurtcam(HurtcamEvent e) {
        if (!hurtcam.getValue())
            e.cancel();
    }

    @EventListener
    public void packet(RecievePacketEvent event) {
        if (mc.theWorld == null) return;
        switch (mode.getValue()) {

            case NORMAL:
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
                    double xz = hori.getValue() * 0.01;
                    double y = vert.getValue() * 0.01;
                    packet.x = (int) xz;
                    packet.y = (int) y;
                    packet.z = (int) xz;
                }

                if (event.getPacket() instanceof S27PacketExplosion) {
                    S27PacketExplosion packetExplosion = (S27PacketExplosion) event.getPacket();
                    packetExplosion.x = packetExplosion.y = packetExplosion.z = 0;
                }
                break;

            case AAC: {
                if (mc.thePlayer.hurtTime >= 1 && mc.thePlayer.hurtTime <= 8) { // 8
                    double yaw = mc.thePlayer.rotationYawHead;
                    yaw = Math.toRadians(yaw);
                    final double dX = -Math.sin(yaw) * 0.05;
                    final double dZ = Math.cos(yaw) * 0.05;
                    mc.thePlayer.motionX = dX;
                    mc.thePlayer.motionZ = dZ;
                }

                break;
            }

            case MMC: {
                if (mc.thePlayer.hurtTime > 0 && mc.thePlayer.hurtTime < 10) {
                    double yaw = mc.thePlayer.rotationYawHead;
                    yaw = Math.toRadians(yaw);
                    final double dX = -Math.sin(yaw) * 0.1;
                    final double dZ = Math.cos(yaw) * 0.1;
                    mc.thePlayer.setPosition(mc.thePlayer.posX + dX, mc.thePlayer.posY - (mc.thePlayer.hurtTime == 5 && !mc.thePlayer.onGround ? 0.05 : 0), mc.thePlayer.posZ + dZ);
                }
                break;
            }
            case HYPIXEL: {
                if (event.getPacket() instanceof S12PacketEntityVelocity || event.getPacket() instanceof S27PacketExplosion)
                    event.cancel();
                break;
            }
        }
    }

    public enum Mode {
        NORMAL, AAC, MMC, HYPIXEL
    }
}
