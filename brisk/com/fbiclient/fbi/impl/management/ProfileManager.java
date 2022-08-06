package com.fbiclient.fbi.impl.management;

import me.xx.api.profile.Profile;
import me.xx.api.profile.ProfileHandler;
import com.fbiclient.fbi.client.management.types.ArrayListManager;
import com.fbiclient.fbi.impl.presets.*;


/**
 * @author Kyle
 * @since 2/9/2018
 **/
public class ProfileManager extends ArrayListManager<Profile> {

    private ProfileHandler handler;

    public void setup() {
        super.setup();
        System.out.println("Loading Config System");
        addConfigs();
        handler = new ProfileHandler();
        handler.setCurrentConfig(new VanillaProfile());
        System.out.println("Finished Loading Config System");
    }

    public ProfileHandler getHandler() {
        return handler;
    }

    public Profile lookup(String search) {
        return getRegistry().stream().filter(config ->
                config.getLabel().replaceAll(" ", "").equalsIgnoreCase(search)
                        || config.getId().equalsIgnoreCase(search)).findFirst().orElse(null);
    }

    public void addConfigs() {
        register(new VanillaProfile(),
                new HypixelProfile(),
                new MineplexProfile());
    }

}
