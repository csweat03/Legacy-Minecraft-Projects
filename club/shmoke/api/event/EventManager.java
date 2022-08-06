package club.shmoke.api.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import club.shmoke.api.event.interfaces.EventListener;

public final class EventManager
{
    private static final Map < Class <? extends Event > , ElementArray<MethodData >> REGISTRY_MAP;

    public static void register(final Object o)
    {
        for (final Method method : o.getClass().getDeclaredMethods())
        {
            if (!isMethodBad(method))
            {
                register(method, o);
            }
        }
    }

    public static void register(final Object o, final Class <? extends Event > clazz)
    {
        for (final Method method : o.getClass().getDeclaredMethods())
        {
            if (!isMethodBad(method, clazz))
            {
                register(method, o);
            }
        }
    }

    public static void unregister(final Object o)
    {
        for (final ElementArray<MethodData> elementArray : EventManager.REGISTRY_MAP.values())
        {
            for (final MethodData methodData : elementArray)
            {
                if (methodData.source.equals(o))
                {
                    elementArray.remove(methodData);
                }
            }
        }

        cleanMap(true);
    }

    public static void unregister(final Object o, final Class <? extends Event > clazz)
    {
        if (EventManager.REGISTRY_MAP.containsKey(clazz))
        {
            for (final MethodData methodData : EventManager.REGISTRY_MAP.get(clazz))
            {
                if (methodData.source.equals(o))
                {
                    EventManager.REGISTRY_MAP.get(clazz).remove(methodData);
                }
            }

            cleanMap(true);
        }
    }

    private static void register(final Method method, final Object o)
    {
        final Class<?> clazz = method.getParameterTypes()[0];
        final MethodData methodData = new MethodData(o, method, method.getAnnotation(EventListener.class).value());

        if (!methodData.target.isAccessible())
        {
            methodData.target.setAccessible(true);
        }

        if (EventManager.REGISTRY_MAP.containsKey(clazz))
        {
            if (!EventManager.REGISTRY_MAP.get(clazz).contains(methodData))
            {
                EventManager.REGISTRY_MAP.get(clazz).add(methodData);
                sortListValue((Class <? extends Event >)clazz);
            }
        }
        else
        {
            EventManager.REGISTRY_MAP.put((Class <? extends Event >)clazz, new ElementArray<MethodData>()
            {
                {
                    this.add(methodData);
                }
            });
        }
    }

    public static void removeEntry(final Class <? extends Event > clazz)
    {
        final Iterator < Map.Entry < Class <? extends Event > , ElementArray<MethodData >>> iterator = EventManager.REGISTRY_MAP.entrySet().iterator();

        while (iterator.hasNext())
        {
            if (iterator.next().getKey().equals(clazz))
            {
                iterator.remove();
                break;
            }
        }
    }

    public static void cleanMap(final boolean b)
    {
        final Iterator < Map.Entry < Class <? extends Event > , ElementArray<MethodData >>> iterator = EventManager.REGISTRY_MAP.entrySet().iterator();

        while (iterator.hasNext())
        {
            if (!b || iterator.next().getValue().isEmpty())
            {
                iterator.remove();
            }
        }
    }

    private static void sortListValue(final Class <? extends Event > clazz)
    {
        final ElementArray<MethodData> elementArray = new ElementArray<MethodData>();

        for (final byte b : EventPriority.VALUE_ARRAY)
        {
            for (final MethodData methodData : EventManager.REGISTRY_MAP.get(clazz))
            {
                if (methodData.priority == b)
                {
                    elementArray.add(methodData);
                }
            }
        }

        EventManager.REGISTRY_MAP.put(clazz, elementArray);
    }

    private static boolean isMethodBad(final Method method)
    {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventListener.class);
    }

    private static boolean isMethodBad(final Method method, final Class <? extends Event > clazz)
    {
        return isMethodBad(method) || !method.getParameterTypes()[0].equals(clazz);
    }

    public static ElementArray<MethodData> get(final Class <? extends Event > clazz)
    {
        return EventManager.REGISTRY_MAP.get(clazz);
    }

    public static void shutdown()
    {
        EventManager.REGISTRY_MAP.clear();
    }

    static
    {
        REGISTRY_MAP = new HashMap < Class <? extends Event > , ElementArray<MethodData >> ();
    }
}
