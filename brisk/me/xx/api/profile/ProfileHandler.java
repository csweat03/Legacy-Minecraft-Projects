package me.xx.api.profile;

import me.xx.api.cheat.Cheat;
import com.fbiclient.fbi.impl.Brisk;

/**
 * @author Kyle
 * @since 2/9/2018
 **/
public class ProfileHandler<T extends Profile> {

    private T currentConfig;

    public T getCurrentConfig() {
        return currentConfig;
    }

    public void setCurrentConfig(T currentConfig) {
        this.currentConfig = currentConfig;
        for (Cheat cheat : Brisk.INSTANCE.getCheatManager().getValues()) {
            if (cheat.getState() && cheat.isBlacklisted())
                cheat.setState(false);
        }
        this.currentConfig.setupRequired();
    }

}
