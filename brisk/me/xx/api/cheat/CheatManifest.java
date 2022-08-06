package me.xx.api.cheat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheatManifest {
    String label();

    String description();

    Category category();

    String[] handles() default {"too", "lazy"};

    boolean visible() default true;
}