package club.shmoke.client.util.manage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kyle
 * @since 8/30/2017
 **/
public abstract class ListManager<T>
{
    private List<T> contents;
    private List<Class> classes;

    public ListManager()
    {
        this.contents = new ArrayList<T>();
        this.classes = new ArrayList<Class>();
    }

    public List<T> getContents()
    {
        return this.contents;
    }

    public void add(final T content)
    {
        this.contents.add(content);
    }

    public void remove(final T content)
    {
        this.contents.remove(content);
    }

    public boolean has(final T content)
    {
        return this.contents.contains(content);
    }
    
    public void include(final T... contents) {
    	Collections.addAll(getContents(), contents);
    }

    public T find(T content)
    {
        for (T con : this.contents)
        {
            if (content == con)
            {
                return con;
            }
        }

        return null;
    }

    public abstract void setup();
}
