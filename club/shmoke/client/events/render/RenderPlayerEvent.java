package club.shmoke.client.events.render;

import club.shmoke.api.event.Event;

public class RenderPlayerEvent extends Event
{
    private final Type type;

    public RenderPlayerEvent(Type type)
    {
        this.type = type;
    }

    public Type getType()
    {
        return type;
    }
}
