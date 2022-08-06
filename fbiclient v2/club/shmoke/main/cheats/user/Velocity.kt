package club.shmoke.main.cheats.user

import club.shmoke.api.cheat.Category
import club.shmoke.api.cheat.Cheat
import club.shmoke.api.cheat.property.Property
import club.shmoke.api.event.EventHandler
import club.shmoke.main.events.PacketEvent
import club.shmoke.main.events.UpdateEvent
import net.minecraft.network.play.server.S12PacketEntityVelocity

/**
 * @author Christian
 */
class Velocity : Cheat("Velocity", 0, Category.USER, "Manipulates the knockback effect.") {

    private val mode = Property(this, "Mode", Mode.CANCEL)

    @EventHandler
    fun onUpdate(event: UpdateEvent) {
        suffix = mode.value.toString() + ""
        if (mode.value == Mode.PUSH) {
            if (mc.thePlayer.hurtTime in 1..4) {
                mc.thePlayer.motionX *= 0.8
                mc.thePlayer.motionZ *= 0.8
            } else if (mc.thePlayer.hurtTime == 6) {
                playerUtility.updatePosition(0.3, -0.2)
            }
        }
    }

    @EventHandler
    fun onPacket(event: PacketEvent) {
        if (mc.thePlayer == null || mc.theWorld == null) return
        if (event.type === PacketEvent.Type.RECEIVE) {
            if (event.packet is S12PacketEntityVelocity) {
                val packet = event.packet as S12PacketEntityVelocity?
                if (packet!!.entityID == mc.thePlayer.entityId) {
                    when (mode.value) {
                        Velocity.Mode.CANCEL -> event.cancel()
                        Velocity.Mode.GROUND -> {
                            if (mc.thePlayer.onGround)
                                playerUtility.updatePosition(0.0, -0.7)
                            else
                                playerUtility.updatePosition(0.0, -1.9)
                        }
                    }
                }
            }
        }
    }

    enum class Mode {
        CANCEL, GROUND, PUSH
    }

}
