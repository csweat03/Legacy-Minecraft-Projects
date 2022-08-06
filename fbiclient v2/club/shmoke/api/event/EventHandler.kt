package club.shmoke.api.event

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author Christian
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.RUNTIME)
annotation class EventHandler
