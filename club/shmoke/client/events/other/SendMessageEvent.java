package club.shmoke.client.events.other;

import club.shmoke.api.event.Event;

public class SendMessageEvent extends Event
{
    public String message;

    public SendMessageEvent(String message)
    {
        this.message = message;
    }
}
