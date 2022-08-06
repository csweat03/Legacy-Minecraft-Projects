package club.shmoke.main.cheats.misc

import club.shmoke.api.cheat.Category
import club.shmoke.api.cheat.Cheat
import club.shmoke.api.cheat.property.Property
import club.shmoke.api.event.EventHandler
import club.shmoke.main.events.UpdateEvent
import net.minecraft.network.play.client.C03PacketPlayer

/**
 * @author Christian
 */
class Damage : Cheat("Damage", 0, Category.MISC, "Allows you to take non-legitimate damage through server exploits.") {

    private val hurt = Property(this, "Hurt", 7, 1, 50, 1)

    private var health: Double = 0.toDouble()

    override fun onEnable() {
        super.onEnable()
        health = mc.thePlayer.health.toDouble()
    }

    @EventHandler
    fun onUpdate(event: UpdateEvent) {
        mc.thePlayer.sendQueue.addToSendQueue(C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + 3.0 + hurt.value.toDouble(), mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround))
        if (mc.thePlayer.health < health)
            toggle()
    }

}
