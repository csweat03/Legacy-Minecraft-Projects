package me.xx.api.management.types;

import java.util.List;

import me.xx.api.management.Manager;

/**
 * @author Kyle
 * Abstract registry that holds data in a list
 * @param <T> Object to register to the registry
 */
public abstract class ListManager<T> extends Manager {

    /**
     * A list of T, used for organizing the data of the registry
     */
    protected List<T> registry;

    protected List<Class> classRegistry;

    /**
     * @return The registry
     */
    public List<T> getRegistry() {
        return registry;
    }

    public List<Class> getClassRegistry() {
        return classRegistry;
    }

    /**
     * Checks if registry contains T
     *
     * @param check
     */
    public boolean has(T check) {
        return registry.contains(check);
    }

    public boolean has(Class check) {
        return classRegistry.contains(check);
    }

    /**
     * Adds T to the registry if not already included
     *
     * @param add the object to be added
     */
    public void include(T add) {
        if (!has(add))
            registry.add(add);
    }

    public void include(Class add) {
        if (!has(add))
            classRegistry.add(add);
    }

    /**
     * Removes T from the registry if included
     *
     * @param remove the object to be removed
     */
    public void remove(T remove) {
        if (has(remove))
            registry.remove(remove);
    }

    public void remove(Class remove) {
        if (has(remove))
            classRegistry.remove(remove);
    }

    /**
     * Takes an array of T and adds all to the registry
     *
     * @param queue the queue to be added
     */
    public void register(T... queue) {
        for (T type : queue) {
            include(type);
        }
    }

    public void register(Class... queue) {
        for (Class type : queue) {
            include(type);
        }
    }

    /**
     * Filter through the registry for the object T for clazz
     *
     * @param clazz the object to find
     * @return T the object found
     */
    public T pull(Class<? extends T> clazz) {
        return  getRegistry().stream().filter(m -> m.getClass() == clazz).findFirst().orElse(null);
    }

}
