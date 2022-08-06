package club.shmoke.client.events.other;

import club.shmoke.api.event.Event;

public class KeypressEvent extends Event
{
    private int key;

    public KeypressEvent(final int key)
    {
        this.key = key;
    }

    public int key()
    {
        return this.key;
    }

    public void setKey(final int key)
    {
        this.key = key;
    }
}
