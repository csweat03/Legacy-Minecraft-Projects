package me.xx.api.event;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Method;

/**
 * @author Kyle
 * @since 2/1/2018
 **/
public class Handler {

    public MethodHandle handler;
    public Object parent;
    public byte priority;
    private final Lookup lookup = MethodHandles.lookup();

    public Handler(Method method, Object parent, byte priority) {
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        MethodHandle m = null;
        try {
            m = lookup.unreflect(method);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (m != null) {
            this.handler = m.asType(
                    m.type().changeParameterType(0, Object.class).changeParameterType(1, Event.class));
        }
        this.parent = parent;
        this.priority = priority;
    }
}
