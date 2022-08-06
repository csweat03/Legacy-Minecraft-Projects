package club.shmoke.client.events.other;

import net.minecraft.network.Packet;
import club.shmoke.api.event.Event;

public class SendPacketEvent extends Event
{
    private Packet packet;

    public SendPacketEvent(Packet packet)
    {
        this.packet = packet;
    }

    public Packet getPacket()
    {
        return this.packet;
    }

    public void setPacket(final Packet packet)
    {
        this.packet = packet;
    }
}
