package club.shmoke.client.events.other;

import club.shmoke.api.event.Event;

/**
 * @author Kyle
 * @since 9/24/2017
 **/
public class MouseEvent extends Event
{
    private int key;

    public MouseEvent(final int key)
    {
        this.key = key;
    }

    public int getKey()
    {
        return this.key;
    }

    public void setKey(final int key)
    {
        this.key = key;
    }
}
