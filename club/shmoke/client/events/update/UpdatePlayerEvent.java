package club.shmoke.client.events.update;

import net.minecraft.client.entity.EntityPlayerSP;
import club.shmoke.api.event.Event;

public class UpdatePlayerEvent extends Event
{
    public boolean alwaysSend;
    public double x, y, z;
    public float yaw, pitch;
    public boolean onGround;
    public Type type;
    public EntityPlayerSP player;

    public UpdatePlayerEvent(double x, double y, double z, float yaw, float pitch, boolean onGround, EntityPlayerSP player)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.player = player;
        this.type = Type.PRE;
    }

    public UpdatePlayerEvent()
    {
        this.type = Type.POST;
    }
}
