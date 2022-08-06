package me.xx.api.event;

/**
 * @author Kyle
 * @since 2/17/2018
 **/
public abstract class Event {

    private boolean cancel, skipFutureCalls;
    private Type type;

    public void fire() {
        EventManager.INSTANCE.dispatch(this);
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(boolean cancelled) {
        this.cancel = cancelled;
    }

    public boolean skippingFutureCalls() {
        return this.skipFutureCalls;
    }

    public void skipFutureCalls(boolean skipFutureCalls) {
        this.skipFutureCalls = skipFutureCalls;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        PRE,
        POST,
        OUTGOING,
        INCOMING;
    }

    public class Priority {
        public static final byte HIGH = 0;
        public static final byte NORMAL = 1;
        public static final byte LOW = 2;
    }

}
