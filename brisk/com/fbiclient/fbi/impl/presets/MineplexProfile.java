package com.fbiclient.fbi.impl.presets;

import me.xx.api.profile.Profile;
import com.fbiclient.fbi.client.anticheat.Anticheats;
import com.fbiclient.fbi.impl.cheats.combat.Killaura;
import com.fbiclient.fbi.impl.cheats.motion.NoFall;
import com.fbiclient.fbi.impl.cheats.motion.Scaffold;
import com.fbiclient.fbi.impl.cheats.motion.Speed;
import com.fbiclient.fbi.impl.cheats.motion.Velocity;

/**
 * @author Kyle
 * @since 3/16/2018
 **/
public class MineplexProfile extends Profile {

    public MineplexProfile() {
        super("Mineplex", Anticheats.GWEN);
    }

    public void setupRequired() {
        if (!ANTIBOT.getState())
            ANTIBOT.setState(true);
        VELOCITY.mode = Velocity.Mode.CANCEL;
        KILLAURA.mode = Killaura.Mode.SINGULAR;
        KILLAURA.event = Killaura.Method.TICK;
        KILLAURA.dura = false;
        KILLAURA.criticals = true;
        KILLAURA.autoblock = true;
        KILLAURA.reach = 4.5;
        KILLAURA.walls = true;
        KILLAURA.aps = 12;
        SCAFFOLD.mode = Scaffold.Mode.NORMAL;
        SPEED.mode = Speed.Mode.MINEPLEX;
        NOFALL.mode = NoFall.Mode.PACKET;
    }

    public void blacklistCheats() {

    }
}
