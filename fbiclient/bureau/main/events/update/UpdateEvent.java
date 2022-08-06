package com.fbiclient.bureau.main.events.update;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateEvent extends Event {

    private static HandlerList handlers = new HandlerList();
    private Speed speed;

    public UpdateEvent(Speed speed) {
        this.speed = speed;
    }

    public Speed getSpeed() {
        return this.speed;
    }

    public HandlerList getHandlers() {
        return UpdateEvent.handlers;
    }

    public static HandlerList getHandlerList() {
        return UpdateEvent.handlers;
    }

    public enum Speed {

        MINUTE64(3840000L),
        MINUTE32(1920000L),
        MINUTE16(960000L),
        MINUTE8(480000L),
        MINUTE5(300000L),
        MINUTE4(240000L),
        MINUTE2(120000L),
        MINUTE(60000L),
        SLOWEST(32000L),
        SLOWER(16000L),
        SLOW(4000L),
        SECOND5(5000L),
        SECOND4(4000L),
        SECOND3(3000L),
        SECOND2(2000L),
        SECOND(1000L),
        FAST(500L),
        FASTER(250L),
        FASTEST(125L),
        TICK2(99L),
        TICK(49L);

        private long time;
        private long last;

        Speed(long time) {
            this.time = time;
            this.last = System.currentTimeMillis();
        }

        public boolean elapsed(long now) {
            if (now - last > time) {
                last = now;
                return true;
            }
            return false;
        }

    }

}
