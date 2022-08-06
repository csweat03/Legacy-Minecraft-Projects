package com.fbiclient.fbi.client.management.types;

import java.util.ArrayList;

import me.xx.api.management.types.ListManager;

/**
 * @author Kyle
 * @param <T> Object to be passed to the ListRegistry
 */
public class ArrayListManager<T> extends ListManager<T> {

    /**
     * Instantiates the registry as an ArrayList
     */
    public void setup(){
        registry = new ArrayList<>();
        classRegistry = new ArrayList<>();
        System.out.println("Setup " + getClass().getSimpleName() + " Registry");
    }

}
