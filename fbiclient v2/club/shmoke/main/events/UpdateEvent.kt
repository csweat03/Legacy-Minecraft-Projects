package club.shmoke.main.events

import club.shmoke.api.event.Event
import net.minecraft.client.entity.EntityPlayerSP

/**
 * @author Christian
 */
class UpdateEvent : Event {

    var type: Type? = Type.POST
    var y: Double = 0.toDouble()
    var yaw: Float = 0.toFloat()
    var pitch: Float = 0.toFloat()

    constructor(y: Double, yaw: Float, pitch: Float) {
        type = Type.PRE
        this.y = y
        this.yaw = yaw
        this.pitch = pitch
    }

    constructor() {
        type = Type.POST
    }

    enum class Type {
        PRE, POST
    }
}
