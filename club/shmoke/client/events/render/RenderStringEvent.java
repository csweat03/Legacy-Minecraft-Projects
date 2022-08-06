package club.shmoke.client.events.render;

import net.minecraft.client.Minecraft;
import club.shmoke.api.event.Event;

public class RenderStringEvent extends Event
{
    private final Minecraft mc;
    private String string;

    public RenderStringEvent(final String string)
    {
        this.mc = Minecraft.getMinecraft();
        this.string = string;
    }

    public String getString()
    {
        return this.string;
    }

    public void setString(final String string)
    {
        this.string = string;
    }
}
