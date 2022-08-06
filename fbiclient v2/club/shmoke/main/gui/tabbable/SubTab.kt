package club.shmoke.main.gui.tabbable

import club.shmoke.api.cheat.Category
import club.shmoke.api.cheat.Cheat
import club.shmoke.api.cheat.property.Property
import java.util.ArrayList

open class SubTab {
    var category: Category? = null
    var cheat: Cheat? = null
    var properties: ArrayList<Property<*>>? = null

    constructor(category: Category, cheat: Cheat) {
        this.category = category
        this.cheat = cheat
        this.properties = cheat.properties
    }

    constructor(cheat: Cheat, property: ArrayList<Property<*>>) {
        this.cheat = cheat
        this.category = cheat.category
        this.properties = property
    }
}