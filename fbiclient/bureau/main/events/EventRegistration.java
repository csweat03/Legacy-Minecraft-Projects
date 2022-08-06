package com.fbiclient.bureau.main.events;

import club.shmoke.main.Plugin;
import com.fbiclient.bureau.main.events.update.UpdateEvent;
import org.bukkit.Bukkit;

public class EventRegistration implements Runnable {

    public EventRegistration(Plugin plugin) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin.API().getPlugin(), this, 0L, 1L);
    }

    @Override
    public void run() {
        long cur = System.currentTimeMillis();

        for (UpdateEvent.Speed speed : UpdateEvent.Speed.values()) {
            if (speed.elapsed(cur)) {
                try {
                    UpdateEvent event = new UpdateEvent(speed);
                    Bukkit.getPluginManager().callEvent(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
