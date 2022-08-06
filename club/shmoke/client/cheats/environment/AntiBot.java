package club.shmoke.client.cheats.environment;

import club.shmoke.api.anticheat.Anticheat;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.Client;
import club.shmoke.client.events.other.RecievePacketEvent;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

import java.util.ArrayList;

public class AntiBot extends Cheat implements IHelper {
    public static ArrayList<EntityPlayer> bots = new ArrayList();
    public Property<Boolean> death = new Property<Boolean>(this, "Dead", true);
    EntityPlayer ep;
    Entity cc;

    public AntiBot() {
        super("AntiBot", Type.PERSONAL);
        this.description = "Removes bots from servers that use killaura-detection bots.";
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent e) {
        Anticheat a = Client.INSTANCE.getAnticheatManager().findAnticheat();
        if (mc.isSingleplayer() || mc.theWorld == null)
            return;

        for (Object obj : mc.theWorld.loadedEntityList)
            if (obj != null && obj instanceof EntityPlayer && obj != mc.thePlayer)
                ep = (EntityPlayer) obj;

        if (ep == null) return;

        switch (a) {
            case WATCHDOG:
                if (ep.isInvisible())
                    mc.theWorld.removeEntity(ep);
                break;
            case CUBECRAFT:
                if (cc != mc.thePlayer && cc instanceof EntityArmorStand) {
                    mc.theWorld.removeEntity(cc);
                }
                break;
            default:
                break;
        }
        if (death.getValue() && ep.isPlayerSleeping())
            mc.theWorld.removeEntity(ep);
    }

    @EventListener
    public void onReceive(RecievePacketEvent e) {
        if (mc.theWorld == null)
            return;
        Anticheat a = Client.INSTANCE.getAnticheatManager().findAnticheat();
        if (a == Anticheat.GWEN) {
            for (EntityPlayer entity : mc.theWorld.playerEntities) {
                if (e.getPacket() instanceof S0CPacketSpawnPlayer) {
                    S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) e.getPacket();
                    double posX = packet.func_148942_f() / 32.0, posY = packet.func_148949_g() / 32.0,
                            posZ = packet.func_148946_h() / 32.0;
                    double difX = mc.thePlayer.posX - posX, difY = mc.thePlayer.posY - posY,
                            difZ = mc.thePlayer.posZ - posZ;
                    double dist = Math.sqrt(difX * difX + difY * difY + difZ * difZ);
                    if (dist > 12.0 || posX == mc.thePlayer.posX || posY == mc.thePlayer.posY || posZ == mc.thePlayer.posZ || mc.thePlayer.ticksExisted < 30)
                        continue;
                    e.cancel();
                }
            }
        }
    }
}