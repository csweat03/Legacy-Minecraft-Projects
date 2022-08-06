package club.shmoke.client.cheats.movement;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import club.shmoke.client.Client;
import club.shmoke.api.anticheat.Anticheat;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.client.events.entity.ItemSlowEvent;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.api.event.interfaces.EventListener;

public class NoSlowDown extends Cheat implements IHelper {

    public NoSlowDown() {
        super("NoSlowDown", Type.MOVEMENT);
    }

    @EventListener
    public void onUpdate(UpdatePlayerEvent e) {
        Anticheat a = Client.INSTANCE.getAnticheatManager().findAnticheat();
        if (a == Anticheat.MMC) {
            if (ENTITY_HELPER.isMoving(mc.thePlayer) && mc.thePlayer.hurtTime == 0) {
                if ((mc.thePlayer.getHeldItem() != null
                        && (mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion
                        || mc.thePlayer.getHeldItem().getItem() instanceof ItemSword
                        || mc.thePlayer.getHeldItem().getItem() instanceof ItemFood)) && mc.thePlayer.getItemInUseDuration() > 2 && mc.thePlayer.getItemInUseDuration() < 8) {
                        mc.thePlayer.setPosition(mc.thePlayer.posX + (mc.thePlayer.motionX * 5), mc.thePlayer.posY + 0.05,
                                mc.thePlayer.posZ + (mc.thePlayer.motionZ * 5));
                }
            }
        }
    }

    @EventListener
    public void onEvent(ItemSlowEvent slow) {
        Anticheat a = Client.INSTANCE.getAnticheatManager().findAnticheat();
        if (a != Anticheat.MMC) {
            if (ENTITY_HELPER.isMoving(mc.thePlayer)) {
                if (mc.thePlayer.isBlocking())
                    unblock();
                slow.setCancelled(true);
            }
        }
    }

    private void unblock() {
        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
                BlockPos.ORIGIN, EnumFacing.DOWN));
    }
}
