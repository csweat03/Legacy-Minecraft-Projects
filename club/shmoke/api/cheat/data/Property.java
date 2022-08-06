package club.shmoke.api.cheat.data;

import java.util.ArrayList;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.util.manage.Module;

public class Property<T> extends Module
{
    private final T def;
    private T value;
    private T min;
    private T max;
    private T inc;

    public Property(Cheat cheat, String label, T value, T min, T max, T inc)
    {
        this.label = label;
        this.id = this.label.toLowerCase().trim();
        this.def = this.value = value;
        this.min = min;
        this.max = max;
        this.inc = inc;
        cheat.addProperties(this);
    }

    public Property(Cheat cheat, String label, T value)
    {
        this.label = label;
        this.id = label.toLowerCase().trim();
        this.def = this.value = value;
        cheat.addProperties(this);
    }
    
    public Property(Cheat cheat, String label)
    {
        this.label = label;
        this.id = label.toLowerCase().trim();
        this.def = this.value = (T) label;
        cheat.addProperties(this);
    }

    public T getMax()
    {
        return max;
    }

    public T getMin()
    {
        return min;
    }

    public String description()
    {
        return description;
    }

    public String label()
    {
        return label;
    }

    public String id()
    {
        return id;
    }

    public T getInc()
    {
        return inc;
    }

    public T getDefault()
    {
        return def;
    }

    public T getValue()
    {
        return value;
    }

    public ArrayList<String> aliases()
    {
        return aliases;
    }

    public String toString()
    {
        if (value instanceof Enum)
        {
            return ((Enum) value).name().toUpperCase();
        }

        return value.toString();
    }

    public void setValue(T value)
    {
        this.value = value;
    }

    public void setMin(T min) {
		this.min = min;
	}

	public void setMax(T max) {
		this.max = max;
	}

	public void setInc(T inc) {
		this.inc = inc;
	}

	public boolean isValid(String name)
    {
        for (Enum e : (Enum[]) this.value)
        {
            if (!e.name().equalsIgnoreCase(name))
            {
                continue;
            }

            return true;
        }

        return false;
    }
}
