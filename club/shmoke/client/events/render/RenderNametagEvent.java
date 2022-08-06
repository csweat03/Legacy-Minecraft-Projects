package club.shmoke.client.events.render;

import net.minecraft.entity.Entity;
import club.shmoke.api.event.Event;

/**
 * @author Kyle
 * @since 10/5/2017
 **/
public class RenderNametagEvent extends Event
{
    private Entity entity;

    public RenderNametagEvent(Entity en)
    {
        this.entity = en;
    }

    public Entity getEntity()
    {
        return entity;
    }
}
