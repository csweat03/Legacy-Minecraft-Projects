package club.shmoke.api.event

/**
 * @author Christian
 */
open class Event {

    var isCancelled: Boolean = false

    fun fire() {
        isCancelled = false
        fire(this)
    }

    fun cancel() {
        isCancelled = true
    }

    private fun fire(event: Event) {
        val dataList = EventManager.GET.get(event.javaClass)

        if (dataList != null)
            for (data in dataList)
                try {
                    data.target.invoke(data.source, event)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

    }

}
