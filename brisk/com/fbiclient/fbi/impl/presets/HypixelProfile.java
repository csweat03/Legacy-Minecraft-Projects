package com.fbiclient.fbi.impl.presets;

import me.xx.api.profile.Profile;
import com.fbiclient.fbi.client.anticheat.Anticheats;
import com.fbiclient.fbi.impl.cheats.combat.Killaura;
import com.fbiclient.fbi.impl.cheats.motion.*;

/**
 * @author Kyle
 * @since 3/16/2018
 **/
public class HypixelProfile extends Profile {

    public HypixelProfile() {
        super("Hypixel", Anticheats.WATCHDOG);
    }

    public void setupRequired() {
        if (!ANTIBOT.getState())
            ANTIBOT.setState(true);
        VELOCITY.mode = Velocity.Mode.CANCEL;
        KILLAURA.mode = Killaura.Mode.SINGULAR;
        KILLAURA.criticals = true;
        KILLAURA.criticalMode = Killaura.Criticals.PACKET;
        KILLAURA.event = Killaura.Method.POST;
        KILLAURA.walls = false;
        KILLAURA.dura = false;
        KILLAURA.autoblock = true;
        KILLAURA.reach = 4;
        KILLAURA.aps = 12;
        SCAFFOLD.tower = true;
        SCAFFOLD.mode = Scaffold.Mode.HYPIXEL;
        NOFALL.mode = NoFall.Mode.GROUND;
        FLIGHT.mode = Flight.Mode.HYPIXEL;
        SPEED.mode = Speed.Mode.HYPIXEL;
    }

    public void blacklistCheats() {
        blacklist(Scaffold.class);
    }

}
