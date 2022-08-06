package club.shmoke.main.cheats.fight;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import club.shmoke.api.event.EventHandler;
import club.shmoke.api.utility.utilities.MathUtility;
import club.shmoke.main.events.PacketEvent;
import club.shmoke.main.events.UpdateEvent;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.util.MathHelper;

public class Antibot extends Cheat {

    private Property<Mode> mode = new Property<>(this, "Mode", Mode.HYPIXEL);

    private EntityPlayer ep;

    public Antibot() {
        super("Antibot", 0, Category.FIGHT, "Removes killaura bots");
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        setSuffix(mode.getValue() + "");
        for (EntityPlayer obj : mc.theWorld.playerEntities)
            if (obj != null && obj != mc.thePlayer)
                ep = obj;

        if (ep == null) return;

        switch (mode.getValue()) {
            case HYPIXEL:
                if ((ep.getDisplayName().getFormattedText().equalsIgnoreCase(ep.getName() + "\247r") && !mc.thePlayer.getDisplayName().getFormattedText().equalsIgnoreCase(mc.thePlayer.getName() + "\247r")) || ep.isInvisible())
                    mc.theWorld.removeEntity(ep);
                break;
            case MINEPLEX:
                //mc.theWorld.playerEntities.forEach(entity -> System.out.println(entity.getHealth()));

                if (mc.thePlayer.getDistanceToEntity(ep) >= 8 || mc.thePlayer.ticksExisted < 30) return;

                double entDiff = Math.sqrt((ep.motionX * ep.motionX) + (ep.motionZ * ep.motionZ) + (ep.motionY * ep.motionY));

                if (entDiff == 0 && ep.posX != ep.prevPosX && ep.posY != ep.prevPosY && ep.posZ != ep.prevPosZ && !Double.isNaN(ep.getHealth())) {
                    mc.theWorld.removeEntity(ep);
                }
                break;
        }
    }

    public enum Mode {
        HYPIXEL, MINEPLEX
    }
}
