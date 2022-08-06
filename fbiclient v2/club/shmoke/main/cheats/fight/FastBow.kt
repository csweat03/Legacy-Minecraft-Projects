package club.shmoke.main.cheats.fight

import club.shmoke.api.cheat.Category
import club.shmoke.api.cheat.Cheat
import club.shmoke.api.cheat.property.Property
import club.shmoke.api.event.EventHandler
import club.shmoke.main.cheats.fight.fastbow.BowAimbot
import club.shmoke.main.cheats.fight.fastbow.Instant
import club.shmoke.main.cheats.fight.fastbow.Kohi
import club.shmoke.main.events.UpdateEvent

/**
 * @author Christian
 */
class FastBow : Cheat("FastBow", 0, Category.FIGHT, "Allows you to shoot arrows better.") {

    private val mode = Property(this, "Mode", Mode.INSTANT)

    private val instant = Instant()
    private val kohi = Kohi()

    @EventHandler
    fun onUpdate(event: UpdateEvent) {
        BowAimbot().aim()
        when (mode.value) {
            FastBow.Mode.KOHI -> kohi.onUpdate(event)
            FastBow.Mode.INSTANT -> instant.onUpdate(event)
        }
    }

    enum class Mode {
        INSTANT, KOHI
    }

}
