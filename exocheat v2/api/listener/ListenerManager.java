package club.shmoke.main.api.listener;

import club.shmoke.main.api.helper.ListManager;
import club.shmoke.main.exocheat.listeners.JoinLeave;

import java.util.ArrayList;
import java.util.List;

public class ListenerManager extends ListManager {

    private List<Listener> listeners = new ArrayList<>();

    public void load() {
        add(new JoinLeave());
        /*
        add(new Example());
        */
    }

    private void add(Listener listener) {
        listeners.add(listener);
    }

    public List<Listener> getContents() {
        return listeners;
    }
}
