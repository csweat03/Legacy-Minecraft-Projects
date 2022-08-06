package club.shmoke.main.events

import club.shmoke.api.event.Event
import net.minecraft.network.Packet

/**
 * @author Christian
 */
class PacketEvent(var packet: Packet<*>?, val type: Type) : Event() {

    enum class Type {
        SEND, RECEIVE
    }

}
