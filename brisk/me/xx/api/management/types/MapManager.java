package me.xx.api.management.types;

import java.util.Collection;
import java.util.Map;

import me.xx.api.management.Manager;

/**
 * @author Kyle
 * Abstract registry that holds data in a map
 * @param <K, V> Object keyPress and value to register to the registry
 */
public abstract class MapManager<K, V> extends Manager {

    /**
     * A Map of K (keyPress), V (value), used for organizing the data of the registry
     */
    protected Map<K, V> registry;

    /**
     * @return The registry
     */
    public Map<K, V> getRegistry() {
        return registry;
    }

    public Collection<V> getValues() {
    	return registry.values();
    }
    
    /**
     * Checks if registry contains T
     * @param check
     */
    public boolean has(K check) {
        return registry.containsKey(check);
    }

    /**
     * Adds K, V to the registry if not already included
     * @param key
     * @param val
     */
    public void include(K key, V val){
        if(!has(key))
            registry.put(key, val);
    }

    /**
     * Looks for the keyPress in registry
     * @param key the keyPress to pull
     * @return the value for K
     */
    public V pull(K key) {
        return registry.get(key);
    }
    
}

