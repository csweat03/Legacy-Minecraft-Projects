package club.shmoke.main.cheats.misc

import club.shmoke.api.cheat.Category
import club.shmoke.api.cheat.Cheat
import club.shmoke.api.event.EventHandler
import club.shmoke.main.events.PacketEvent
import net.minecraft.network.play.client.C00PacketKeepAlive

/**
 * @author Christian
 */
class Ping : Cheat("Ping", 0, Category.MISC, "Allows you to increase your ping.") {

    @EventHandler
    fun onPacket(event: PacketEvent) {
        if (event.type === PacketEvent.Type.SEND && event.packet is C00PacketKeepAlive) {
            try {
                Thread.sleep(1500)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}
