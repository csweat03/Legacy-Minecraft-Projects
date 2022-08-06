package club.shmoke.api.event

import java.lang.reflect.Method

/**
 * @author Christian
 */
class EventData internal constructor(var source: Any, var target: Method)
