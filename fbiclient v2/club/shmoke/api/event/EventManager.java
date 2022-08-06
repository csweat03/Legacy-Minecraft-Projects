package club.shmoke.api.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Christian
 */
public enum EventManager {

    GET;

    private final Map<Class<? extends Event>, ElementArray<EventData>> REGISTRY_MAP = new HashMap<>();

    public void register(Object object) {
        for (Method method : object.getClass().getDeclaredMethods())
            if (!isMethodBad(method)) register(method, object);
    }

    public void unregister(Object object) {
        for (ElementArray<EventData> elementArray : REGISTRY_MAP.values())
            for (EventData methodData : elementArray)
                if (methodData.getSource().equals(object))
                    elementArray.remove(methodData);

        cleanMap(true);
    }

    public void cleanMap(boolean b) {
        Iterator<Map.Entry<Class<? extends Event>, ElementArray<EventData>>> iterator = REGISTRY_MAP.entrySet().iterator();

        while (iterator.hasNext())
            if (!b || iterator.next().getValue().isEmpty())
                iterator.remove();
    }

    public ElementArray<EventData> get(final Class<? extends Event> clazz) {
        return REGISTRY_MAP.get(clazz);
    }

    private boolean isMethodBad(final Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventHandler.class);
    }

    private void register(Method method, Object object) {
        Class<?> clazz = method.getParameterTypes()[0];
        EventData methodData = new EventData(object, method);

        if (!methodData.getTarget().isAccessible())
            methodData.getTarget().setAccessible(true);

        if (REGISTRY_MAP.containsKey(clazz)) {
            if (!REGISTRY_MAP.get(clazz).contains(methodData))
                REGISTRY_MAP.get(clazz).add(methodData);
        } else
            REGISTRY_MAP.put((Class<? extends Event>) clazz, new ElementArray<EventData>() {{
                add(methodData);
            }});
    }
}
