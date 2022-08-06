package com.fbiclient.bureau.api.command;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandManifest {
    String label() default "LABEL";

    String syntax() default "SYNTAX";

    String[] triggers() default {"TRIGGERS"};

    boolean runnable() default true;

}
