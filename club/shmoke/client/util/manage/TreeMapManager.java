package club.shmoke.client.util.manage;

import java.util.Collection;
import java.util.TreeMap;

/**
 * @author Kyle
 * @since 9/25/2017
 **/
public abstract class TreeMapManager<K, V>
{
    private TreeMap<K, V> contentMap = new TreeMap<>();

    public TreeMap getContents()
    {
        return contentMap;
    }

    public Collection<V> getValues()
    {
        return contentMap.values();
    }

    public Collection<K> getKeys()
    {
        return contentMap.keySet();
    }

    public boolean has(K key)
    {
        return contentMap.containsKey(key);
    }

    public void add(K key, V val)
    {
        contentMap.put(key, val);
    }

    public void remove(K key)
    {
        contentMap.remove(key);
    }

    public abstract void setup();
}
