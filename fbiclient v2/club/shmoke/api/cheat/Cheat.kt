package club.shmoke.api.cheat

import club.shmoke.api.cheat.property.Property
import club.shmoke.api.event.EventManager
import club.shmoke.api.utility.utilities.Styler
import club.shmoke.api.utility.Utility
import java.util.*

open class Cheat(val label: String, var key: Int = 0, val category: Category, val description: String = "") : Utility() {

    var state = false
    var visible = true
    var suffix = ""
    var width: Int = 0
    val properties = ArrayList<Property<*>>()
    var shift = false

    fun toggle() {
        state = !state
        if (state)
            onEnable()
        else
            onDisable()
    }

    fun addProperty(vararg property: Property<*>) {
        Collections.addAll(properties, *property)
    }

    fun hasSuffix(): Boolean {
        return suffix != ""
    }

    open fun onEnable() {
        EventManager.GET.register(this)
        width = font.getStringWidth(Styler.switchCase(label + (if (hasSuffix()) ": $suffix" else "")))
    }

    open fun onDisable() {
        EventManager.GET.unregister(this)
    }
}

enum class Category {
    FIGHT, MISC, MOVE, USER, VIEW
}