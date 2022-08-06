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
class Kohi : Utility() {

    fun onUpdate(event: UpdateEvent) {
        if (!playerUtility.isBowing(1)) return

        for (i in 0..14)
            mc.thePlayer.sendQueue.addToSendQueue(C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true))

        mc.thePlayer.setItemInUse(null, 0)
        mc.thePlayer.sendQueue.addToSendQueue(C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN))
    }

}
