package club.shmoke.main.events;

import club.shmoke.api.event.Event;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.util.IChatComponent;

import java.util.List;

public class ChatEvent extends Event {

    private IChatComponent component;
    private List<ChatLine> chatLines;
    private String message;
    private Type type;

    public ChatEvent(IChatComponent component, List<ChatLine> chatLines) {
        this.component = component;
        this.chatLines = chatLines;
        this.type = Type.RECEIVE;
    }

    public ChatEvent(String message) {
        this.message = message;
        this.type = Type.SEND;
    }

    public IChatComponent getComponent() {
        return component;
    }

    public List<ChatLine> getChatLines() {
        return chatLines;
    }

    public String getMessage() {
        return message;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        SEND, RECEIVE
    }

}
