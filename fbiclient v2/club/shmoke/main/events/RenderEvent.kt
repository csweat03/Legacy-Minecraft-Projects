package club.shmoke.main.events

import club.shmoke.api.event.Event
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.Entity

class RenderEvent() : Event() {

    var type: Type = Type.OVERLAY

    constructor(entity: Entity) : this() {
        type = Type.NAMETAGS
    }

    constructor(partialTicks: Float, world: WorldClient) : this() {
        type = Type.WORLD
    }

    constructor(partialTicks: Float) : this() {
        type = Type.ESP
    }

    constructor(title: String, x: Int, y: Int) : this() {
        type = Type.SCOREBOARD
    }

    enum class Type {
        OVERLAY, NAMETAGS, WORLD, SCOREBOARD, ESP
    }
}