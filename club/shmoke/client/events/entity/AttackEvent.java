package club.shmoke.client.events.entity;

import net.minecraft.entity.Entity;
import club.shmoke.api.event.Event;

public class AttackEvent extends Event
{
    private Entity inEntity;

    public AttackEvent(Entity inEntity)
    {
        this.inEntity = inEntity;
    }
}
