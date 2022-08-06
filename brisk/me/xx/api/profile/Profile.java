package me.xx.api.profile;

import me.xx.api.cheat.Cheat;
import com.fbiclient.fbi.client.anticheat.Anticheats;
import com.fbiclient.fbi.client.framework.helper.ICheats;
import com.fbiclient.fbi.client.management.types.ArrayListManager;
import com.fbiclient.fbi.impl.Brisk;

/**
 * @author Kyle
 * @since 2/9/2018
 **/
public abstract class Profile implements ICheats {

    private String label, id;
    private Anticheats anticheat;

    private BlacklistedManager blackManager;

    public Profile(String label, Anticheats antiCheat) {
        this.setLabel(label);
        this.setId(getLabel().toLowerCase().replaceAll(" ", ""));
        this.anticheat = antiCheat;
        //this.setId(this.getClass().getSimpleName().replaceAll("Profile", ""));
        (blackManager = new BlacklistedManager()).setup();
        blacklistCheats();
    }

    public abstract void setupRequired();

    public abstract void blacklistCheats();

    public String getLabel() {
        return label;
    }

    public void setLabel(String str) {
        label = str;
    }

    public String getId() {
        return id;
    }

    public void setId(String str) {
        id = str;
    }

    public boolean isCurrent() {
        return (Brisk.INSTANCE.getConfigManager().getHandler().getCurrentConfig() == this);
    }

    public Anticheats getAnticheat() {
        return anticheat;
    }

    public void blacklist(Class<? extends Cheat>... cheats) {
        for(Class clazz : cheats) {
            Cheat cheat = Brisk.INSTANCE.getCheatManager().lookup(clazz);
            blackManager.include(cheat);
        }
    }

    public BlacklistedManager getBlacklists() {
        return blackManager;
    }

    public class BlacklistedManager extends ArrayListManager<Cheat> {
    }

}
