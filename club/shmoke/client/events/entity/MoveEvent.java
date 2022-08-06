package club.shmoke.client.events.entity;

import club.shmoke.api.event.Event;

public class MoveEvent extends Event
{
    public double x, y, z;

    public MoveEvent(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getZ()
    {
        return z;
    }
}
