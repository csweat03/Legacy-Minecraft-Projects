package club.shmoke.api.cheat;

import club.shmoke.api.event.EventManager;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.Client;
import club.shmoke.client.cheats.combat.Killaura;
import club.shmoke.client.cheats.environment.*;
import club.shmoke.client.cheats.movement.*;
import club.shmoke.client.cheats.personal.Inventory;
import club.shmoke.client.cheats.personal.Scaffold;
import club.shmoke.client.cheats.personal.Velocity;
import club.shmoke.client.cheats.visual.*;
import club.shmoke.client.events.other.KeypressEvent;
import club.shmoke.client.util.GameLogger;
import club.shmoke.client.util.manage.ListManager;

import java.util.ArrayList;
import java.util.Comparator;

public class CheatManager extends ListManager<Cheat> {
    public CheatManager() {
        EventManager.register(this);
    }

    public void registerCheats() {
        include(
                new FastInteract(), new AutoInteract(), new Friends(), new NoFall(), new Phase(), new BetterCommands(),
                new Killaura(), new SpawnRun(), new LongJump(), new NoSlowDown(), new Speed(), new Step(),
                new Flight(), new Jesus(), new AntiBot(), new Inventory(), new Velocity(),
                new Scaffold(), new ClickGui(), new Overlay(), new ESP(), new PlayerController(),
                new Animations(), new Ambience(), new PlayerModel())
        ;
    }

    public void setup() {
        GameLogger.log("Loading Horizon... 48% Complete", false, GameLogger.Type.SYSTEM);
        registerCheats();
        try {
            for (Cheat c : getContents()) {
                c.setId(c.id);
                c.setLabel(c.label);
                c.setType(c.type());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        GameLogger.log("Loading Horizon... 64% Complete", false, GameLogger.Type.SYSTEM);
    }

    public Cheat get(Class<? extends Cheat> clazz) {
        for (Cheat mod : getContents()) {
            if (mod.getClass() == clazz) {
                return mod;
            }
        }

        return null;
    }

    public Cheat get(String name) {
        for (Cheat cheat : getContents()) {
            if (cheat.id().equalsIgnoreCase(name)) {
                return cheat;
            }
        }
        return null;
    }

    public ArrayList<Cheat> getCheatsInType(Cheat.Type type) {
        ArrayList<Cheat> mods = new ArrayList<Cheat>();

        for (Cheat mod : this.getContents()) {
            if (mod.isType(type) && !mod.getClass().equals(Overlay.class)) {
                mods.add(mod);
            }
        }

        return mods;
    }

    public ArrayList<Cheat> getCheatsForRendering() {
        ArrayList<Cheat> sortedMods = new ArrayList<>();
        sortedMods.addAll(Client.INSTANCE.getCheatManager().getContents());
        sortedMods.sort(Comparator.comparing(Cheat::width).reversed());
        return sortedMods;
    }

    @EventListener
    public void call(KeypressEvent e) {
        for (Cheat cheat : Client.INSTANCE.getCheatManager().getContents()) {
            if (e.key() == cheat.macro()) {
                cheat.toggle();
            }
        }
    }
}
