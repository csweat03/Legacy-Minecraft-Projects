package club.shmoke.main.cheats.fight.fastbow

import club.shmoke.api.utility.Utility
import club.shmoke.main.events.UpdateEvent
import net.minecraft.network.play.client.C03PacketPlayer
import net.minecraft.network.play.client.C07PacketPlayerDigging
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing

/**
 * @author Christian
 */
class Instant : Utility() {

    fun onUpdate(event: UpdateEvent) {
        if (playerUtility.isBowing(0)) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem())
            mc.thePlayer.inventory.getCurrentItem()!!.item.onItemRightClick(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer)
            for (i in 0..74)
                mc.thePlayer.sendQueue.addToSendQueue(C03PacketPlayer(false))
            mc.netHandler!!.addToSendQueue(C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN))
            mc.thePlayer.inventory.getCurrentItem()!!.item.onPlayerStoppedUsing(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer, 10)
        }
    }

}
