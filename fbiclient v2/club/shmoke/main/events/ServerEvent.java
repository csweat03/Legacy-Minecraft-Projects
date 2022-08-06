package club.shmoke.main.events;

import club.shmoke.api.event.Event;

public class ServerEvent extends Event {

    private Type type;

    public ServerEvent(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        JOIN, LEAVE
    }

}
