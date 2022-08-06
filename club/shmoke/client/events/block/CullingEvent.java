package club.shmoke.client.events.block;

import club.shmoke.api.event.Event;

public class CullingEvent extends Event
{
    public CullingEvent(boolean cancelled)
    {
        setCancelled(cancelled);
    }
}
