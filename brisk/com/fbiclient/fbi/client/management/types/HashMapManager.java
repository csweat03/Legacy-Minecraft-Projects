package com.fbiclient.fbi.client.management.types;

import java.util.HashMap;

import me.xx.api.management.types.MapManager;

/**
 * @author Kyle
 * @param <K, V> Object keyPress and value to be passed to the MapRegistry
 */
public class HashMapManager<K, V> extends MapManager<K, V> {

    /**
     * Instantiates the registry as an HashMap
     */
    public void setup(){
        registry = new HashMap<>();
        System.out.println("Setup " + getClass().getSimpleName() + " Registry");

    }

}