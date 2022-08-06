package com.fbiclient.fbi.client.framework.helper;

import com.fbiclient.fbi.impl.Brisk;
import com.fbiclient.fbi.impl.cheats.combat.Killaura;
import com.fbiclient.fbi.impl.cheats.misc.AntiBot;
import com.fbiclient.fbi.impl.cheats.motion.Flight;
import com.fbiclient.fbi.impl.cheats.motion.LongJump;
import com.fbiclient.fbi.impl.cheats.motion.NoFall;
import com.fbiclient.fbi.impl.cheats.motion.NoSlowdown;
import com.fbiclient.fbi.impl.cheats.motion.Scaffold;
import com.fbiclient.fbi.impl.cheats.motion.Speed;
import com.fbiclient.fbi.impl.cheats.motion.Velocity;
import com.fbiclient.fbi.impl.cheats.visual.ClickableGui;
import com.fbiclient.fbi.impl.cheats.visual.Xray;
import com.fbiclient.fbi.impl.gui.hud.Hud;

/**
 * @author Kyle
 * @since 3/22/2018
 **/
public interface ICheats {
    Killaura KILLAURA = (Killaura) Brisk.INSTANCE.getCheatManager().lookup(Killaura.class);
    Speed SPEED = (Speed) Brisk.INSTANCE.getCheatManager().lookup(Speed.class);
    LongJump LONGJUMP = (LongJump) Brisk.INSTANCE.getCheatManager().lookup(LongJump.class);
    Flight FLIGHT = (Flight) Brisk.INSTANCE.getCheatManager().lookup(Flight.class);
    Velocity VELOCITY = (Velocity) Brisk.INSTANCE.getCheatManager().lookup(Velocity.class);
    Scaffold SCAFFOLD = (Scaffold) Brisk.INSTANCE.getCheatManager().lookup(Scaffold.class);
    NoFall NOFALL = (NoFall) Brisk.INSTANCE.getCheatManager().lookup(NoFall.class);
    Xray XRAY = (Xray) Brisk.INSTANCE.getCheatManager().lookup(Xray.class);
    Hud HUD = (Hud) Brisk.INSTANCE.getCheatManager().lookup(Hud.class);
    NoSlowdown NOSLOW = (NoSlowdown) Brisk.INSTANCE.getCheatManager().lookup(NoSlowdown.class);
    ClickableGui CLICKABLE = (ClickableGui) Brisk.INSTANCE.getCheatManager().lookup(ClickableGui.class);
    AntiBot ANTIBOT = (AntiBot) Brisk.INSTANCE.getCheatManager().lookup(AntiBot.class);
}