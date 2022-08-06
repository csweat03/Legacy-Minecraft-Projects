package club.shmoke.main.api.check;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Christian
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckInfo {

    String label() default "check";
    boolean silent() default true;
    boolean active() default true;
    boolean ban() default false;
    boolean debug() default false;
    int alertsTillBan() default 10;

}
