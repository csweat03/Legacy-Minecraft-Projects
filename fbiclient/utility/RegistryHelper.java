package com.fbiclient.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class RegistryHelper<T> {

    private T obj;
    private List<T> objects = new ArrayList<>();

    public abstract void initialize();

    public List<T> getContents() {
        return objects;
    }

    protected void addContent(T... obj) {
        Collections.addAll(objects, obj);
    }

    public boolean hasContent(Class clazz) {
        return getContent(clazz) != null;
    }

    public T getContent(Class<? extends T> clazz) {
        for (Object obj : objects)
            if (clazz == obj.getClass())
                return (T) obj;
        Logger.write("Exception Thrown! Could not find safe return value. Current class directory: RegistryHelper.class:26", Logger.Level.ERROR);
        return null;
    }

}
