package club.shmoke.client.events.render;

import net.minecraft.entity.Entity;
import club.shmoke.api.event.Event;

public class RenderEntityEvent extends Event
{
    private final Type type;
    private final Entity entity;

    public RenderEntityEvent(Type type, Entity entity)
    {
        this.type = type;
        this.entity = entity;
    }

    public Type getType()
    {
        return type;
    }

    public Entity getEntity()
    {
        return entity;
    }
}
