package club.shmoke.client.events.other;

import net.minecraft.network.Packet;
import club.shmoke.api.event.Event;

public class RecievePacketEvent extends Event
{
    private Packet packet;

    public Packet getPacket()
    {
        return this.packet;
    }

    public void setPacket(final Packet packet)
    {
        this.packet = packet;
    }

    public RecievePacketEvent(final Packet packet)
    {
        this.packet = packet;
    }
}
