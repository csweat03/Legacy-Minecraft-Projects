package com.fbiclient.bureau.main.checks.movement.attrib;

import com.fbiclient.bureau.main.players.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

public abstract class IMAttribute {

    public abstract void onExecution(PlayerMoveEvent event);

    protected CustomPlayer getCPlayer(PlayerMoveEvent event) {
        return new CustomPlayer(event);
    }


}
