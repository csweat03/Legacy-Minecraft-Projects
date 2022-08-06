package club.shmoke.api.command

import club.shmoke.api.utility.Utility

abstract class Command(var label: String, var syntax: String, var alias: Array<String>) : Utility() {
    abstract fun dispatch(args: Array<String>, message: String)
}