package club.shmoke.main.cheats.misc

import club.shmoke.api.cheat.Category
import club.shmoke.api.cheat.Cheat
import club.shmoke.api.cheat.property.Property
import club.shmoke.api.event.EventHandler
import club.shmoke.main.events.UpdateEvent
import org.lwjgl.input.Keyboard

/**
 * @author Christian
 */
class Sprint : Cheat("Sprint", 0, Category.MISC, "Forces the sprint keybind to be pressed.") {

    private val omni = Property(this, "Omni", true)

    @EventHandler
    fun onUpdate(event: UpdateEvent) {
        if (mc.thePlayer.speed <= 0.05) return
        mc.thePlayer.isSprinting = true
    }

    override fun onDisable() {
        super.onDisable()
        mc.gameSettings.keyBindSprint.isPressed = false
        mc.thePlayer.isSprinting = false
    }

}
