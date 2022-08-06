package club.shmoke.client.cheats.environment;

import net.minecraft.entity.player.EntityPlayer;
import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.events.other.MouseEvent;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.util.GameLogger;

public class Friends extends Cheat {

    public Friends() {
        super("Friends", Type.ENVIRONMENT);
    }

    @EventListener
    public void onMiddle(MouseEvent event) {
        if (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null || event.getKey() != 2) return;
        EntityPlayer entityPlayer = (EntityPlayer) mc.objectMouseOver.entityHit;

        if (!Client.INSTANCE.getFriendManager().has(entityPlayer.getName())) {
            Client.INSTANCE.getFriendManager().add(entityPlayer.getName(), entityPlayer.getName());
            GameLogger.log(String.format("\247aYou have added \2477%s \247aas a friend.", entityPlayer.getName()), GameLogger.Type.INFO);
        } else {
            Client.INSTANCE.getFriendManager().remove(entityPlayer.getName());
            GameLogger.log(String.format("\247aYou have removed \2477%s \247afrom your friends.", entityPlayer.getName()), GameLogger.Type.INFO);
        }
    }

}
