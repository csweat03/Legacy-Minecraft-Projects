package com.fbiclient.fbi.impl.presets;

import me.xx.api.profile.Profile;
import com.fbiclient.fbi.client.anticheat.Anticheats;
import com.fbiclient.fbi.impl.cheats.motion.Velocity;

/**
 * @author Kyle
 * @since 3/16/2018
 **/
public class VanillaProfile extends Profile {

    public VanillaProfile() {
        super("Vanilla", Anticheats.UNKNOWN);
    }

    public void setupRequired() {
        VELOCITY.mode = Velocity.Mode.CANCEL;
    }

    public void blacklistCheats() {

    }
}
