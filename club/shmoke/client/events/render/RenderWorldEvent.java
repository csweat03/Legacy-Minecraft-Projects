package club.shmoke.client.events.render;

import club.shmoke.api.event.Event;

public class RenderWorldEvent extends Event
{
    private float partialTicks;

    public RenderWorldEvent(float partialTicks)
    {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks()
    {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks)
    {
        this.partialTicks = partialTicks;
    }
}
