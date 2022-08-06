package club.shmoke.client.events.other;

import club.shmoke.api.event.Event;

/**
 * @author Kyle
 * @since 10/13/2017
 **/
public class RecieveMessageEvent extends Event
{
    private String msg;

    public RecieveMessageEvent(String msg)
    {
        this.msg = msg;
    }

    public String getMessage()
    {
        return msg;
    }
}
