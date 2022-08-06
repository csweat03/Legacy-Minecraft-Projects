package club.shmoke.api.event;

import java.lang.reflect.InvocationTargetException;

public class Event
{
    private boolean cancelled;

    public Event fire()
    {
        this.cancelled = false;
        fire(this);
        return this;
    }

    public boolean isCancelled()
    {
        return this.cancelled;
    }
    
    public void cancel() {
    	this.cancelled = true;
    }

    public void setCancelled(final boolean state)
    {
        this.cancelled = state;
    }

    private static final void fire(final Event event)
    {
        final ElementArray<MethodData> dataList = EventManager.get(event.getClass());

        if (dataList != null)
        {
            for (final MethodData data : dataList)
            {
                try
                {
                    data.target.invoke(data.source, event);
                }
                catch (IllegalAccessException e2)
                {
                    System.out.println("Can't invoke '" + data.target.getName() + "' because it's not accessible.");
                }
                catch (IllegalArgumentException e3)
                {
                    System.out.println("Can't invoke '" + data.target.getName() + "' because the parameter/s don't match.");
                }
                catch (InvocationTargetException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public enum Type
    {
        PRE("PRE", 0),
        POST("POST", 1);

        private Type(final String s, final int n)
        {
        }
    }
}
