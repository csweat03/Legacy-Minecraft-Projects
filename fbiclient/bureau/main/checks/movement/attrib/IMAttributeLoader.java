package com.fbiclient.bureau.main.checks.movement.attrib;

import com.fbiclient.bureau.main.checks.movement.attrib.y.Limit;
import com.fbiclient.bureau.main.checks.movement.attrib.y.Solid;
import org.bukkit.event.player.PlayerMoveEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class IMAttributeLoader {

    private final Solid SOLID = new Solid();
    private final Limit LIMIT = new Limit();

    /* Load all attributes specified in the fields above */
    public void loadAttributes() {
        /* Assuming all above fields are instances of IMAttribute.class */
        for (Field field : getClass().getDeclaredFields()) {
            try {
                Class clazz = field.getDeclaringClass();
                clazz.newInstance();
            } catch (Exception ex) {
                System.out.println("Incorrect Class! Please check all classes contain a superclass of IMAttribute..");
            }
        }
    }

    /* Executes all attributes specified in the fields above */
    public void executeAttributes(PlayerMoveEvent event) {
        for (Field field : getClass().getDeclaredFields()) {
            try {
                Class clazz = field.getType();
                Object newInstance = clazz.newInstance();
                Method method = clazz.getDeclaredMethod("onExecution", PlayerMoveEvent.class);
                method.setAccessible(true);
                method.invoke(newInstance, event);
            } catch (Exception ex) {
                System.out.println("Incorrect Class! Please check all classes contain a superclass of IMAttribute..");
            }
        }
    }

}
