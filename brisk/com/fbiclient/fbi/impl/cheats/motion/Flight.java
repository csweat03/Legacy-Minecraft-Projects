package com.fbiclient.fbi.impl.cheats.motion;

import me.valkyrie.api.value.Val;
import me.valkyrie.api.value.types.constrain.Clamp;
import me.valkyrie.api.value.types.constrain.Increment;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.entity.LivingUpdateEvent;
import com.fbiclient.fbi.client.events.game.TickEvent;
import com.fbiclient.fbi.client.events.player.MoveEvent;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import com.fbiclient.fbi.client.framework.helper.ICheats;
import com.fbiclient.fbi.impl.cheats.motion.flight.FlightModes;

@CheatManifest(label = "Flight", description = "Walk on air", category = Category.MOTION, handles = {"fly"})
public class Flight extends Cheat implements ICheats, FlightModes {

    @Val(label = "Mode", description = "The mode of flying")
    public Mode mode = Mode.NORMAL;

    @Val(label = "Vertical")
    @Clamp(min = "0.1", max = "5.0")
    @Increment(value = "0.1")
    public double vertical = 1;

    @Val(label = "Horizontal")
    @Clamp(min = "0.1", max = "5.0")
    @Increment(value = "0.1")
    public double horizontal = 1;

    private int ticks;

    @Override
    public void onDisable() {
        mc.thePlayer.capabilities.isFlying = false;
        mc.timer.timerSpeed = 1;
        mc.thePlayer.setSpeed(MOTION_HELPER.getBaseMoveSpeed());
    }

    @Override
    public void onEnable() {
    }

    @Register
    public void handleMove(MoveEvent event) {
        switch (mode) {
            case NORMAL:
                if (mc.thePlayer.isMoving())
                    mc.thePlayer.setSpeed(horizontal);
                break;
            case MINEPLEX:
                MINEPLEX.dabOnSaidHaters();
                break;
        }
    }

    @Register
    public void handleMotionUpdates(UpdateMotionEvent e) {
        switch (e.getType()) {
            case PRE:
                mc.timer.updateTimer();
                if (SPEED.getState())
                    SPEED.setState(false);
                break;
            case POST:
                break;
        }
    }

    @Register
    public void handleTicking(TickEvent e) {
        mc.timer.updateTimer();
        suffix = (mode.name());
    }

    @Register
    public void handleLivingUpdates(LivingUpdateEvent event) {
        switch (mode) {
            case HYPIXEL: {
                HYPIXEL.evenMoreDab();
                break;
            }
        }
    }

    public enum Mode {
        NORMAL, HYPIXEL, MINEPLEX
    }

}