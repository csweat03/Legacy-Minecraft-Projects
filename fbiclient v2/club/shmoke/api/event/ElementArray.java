package club.shmoke.api.event;

import java.util.Iterator;

/**
 * @author Christian
 */
public class ElementArray<T> implements Iterable<T>
{
    private T[] elements;

    public ElementArray(T[] array)
    {
        elements = array;
    }

    public ElementArray()
    {
        elements = (T[]) new Object[0];
    }

    public void add(T t)
    {
        if (t != null)
        {
            Object[] array = new Object[size() + 1];

            for (int i = 0; i < array.length; ++i)
            {
                if (i < size())
                {
                    array[i] = get(i);
                }
                else
                {
                    array[i] = t;
                }
            }

            set((T[]) array);
        }
    }

    public void remove(T t)
    {
        if (contains(t))
        {
            Object[] array = new Object[size() - 1];
            boolean b = true;

            for (int i = 0; i < size(); ++i)
            {
                if (b && get(i).equals(t))
                {
                    b = false;
                }
                else
                {
                    array[b ? i : (i - 1)] = get(i);
                }
            }

            set((T[]) array);
        }
    }

    public boolean contains(T t)
    {
        Object[] array;

        for (int length = (array = array()).length, i = 0; i < length; ++i)
        {
            T entry = (T)array[i];

            if (entry.equals(t))
            {
                return true;
            }
        }

        return false;
    }

    private void set(T[] array)
    {
        elements = array;
    }

    public void clear()
    {
        elements = (T[]) new Object[0];
    }

    public T get(int index)
    {
        return array()[index];
    }

    public int size()
    {
        return array().length;
    }

    public T[] array()
    {
        return elements;
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new Iterator<T>()
        {
            private int index = 0;
            @Override
            public boolean hasNext()
            {
                return index < ElementArray.this.size() && ElementArray.this.get(index) != null;
            }
            @Override
            public T next()
            {
                return ElementArray.this.get(index++);
            }
            @Override
            public void remove()
            {
                ElementArray.this.remove(ElementArray.this.get(index));
            }
        };
    }
}