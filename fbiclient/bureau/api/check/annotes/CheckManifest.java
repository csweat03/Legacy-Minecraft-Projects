package com.fbiclient.bureau.api.check.annotes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckManifest {
    String label() default "LABEL";

    String[] authors() default {"Christian"};

    int vio() default 10;

}
