package club.shmoke.client.preset.presets;

import club.shmoke.client.Client;
import club.shmoke.api.anticheat.Anticheat;
import club.shmoke.client.cheats.combat.Killaura;
import club.shmoke.client.cheats.environment.FastInteract;
import club.shmoke.client.cheats.environment.Friends;
import club.shmoke.client.cheats.environment.NoFall;
import club.shmoke.client.cheats.movement.Flight;
import club.shmoke.client.cheats.movement.NoSlowDown;
import club.shmoke.client.cheats.movement.Speed;
import club.shmoke.client.cheats.movement.Step;
import club.shmoke.client.cheats.environment.AntiBot;
import club.shmoke.client.cheats.personal.Velocity;
import club.shmoke.client.cheats.visual.Overlay;
import club.shmoke.client.preset.Config;

public class Mineplex extends Config
{
    public Mineplex()
    {
        super("Mineplex", Anticheat.GWEN);
    }

    public void setEnabled()
    {
        // Motion
        if (Client.INSTANCE.getCheatManager().get(Flight.class).getState())
        {
            Client.INSTANCE.getCheatManager().get(Flight.class).toggle();
        }

        if (Client.INSTANCE.getCheatManager().get(Speed.class).getState())
        {
            Client.INSTANCE.getCheatManager().get(Speed.class).toggle();
        }

        if (!Client.INSTANCE.getCheatManager().get(Velocity.class).getState())
        {
            Client.INSTANCE.getCheatManager().get(Velocity.class).toggle();
        }

        if (!Client.INSTANCE.getCheatManager().get(NoSlowDown.class).getState())
        {
            Client.INSTANCE.getCheatManager().get(NoSlowDown.class).toggle();
        }

        if (!Client.INSTANCE.getCheatManager().get(Step.class).getState())
        {
            Client.INSTANCE.getCheatManager().get(Step.class).toggle();
        }

        // Attack
        if (Client.INSTANCE.getCheatManager().get(Killaura.class).getState())
        {
            Client.INSTANCE.getCheatManager().get(Killaura.class).toggle();
        }

        // Visual
        
//        if (Client.INSTANCE.getCheatManager().get(Xray.class).getState())
//        {
//            Client.INSTANCE.getCheatManager().get(Xray.class).toggle();
//        }

        if (!Client.INSTANCE.getCheatManager().get(Overlay.class).getState())
        {
            Client.INSTANCE.getCheatManager().get(Overlay.class).toggle();
        }

        // World
        if (Client.INSTANCE.getCheatManager().get(FastInteract.class).getState())
        {
            Client.INSTANCE.getCheatManager().get(FastInteract.class).toggle();
        }

        if (!Client.INSTANCE.getCheatManager().get(Friends.class).getState())
        {
            Client.INSTANCE.getCheatManager().get(Friends.class).toggle();
        }

        if (!Client.INSTANCE.getCheatManager().get(NoFall.class).getState())
        {
            Client.INSTANCE.getCheatManager().get(NoFall.class).toggle();
        }

        // User
        if (!Client.INSTANCE.getCheatManager().get(AntiBot.class).getState())
        {
            Client.INSTANCE.getCheatManager().get(AntiBot.class).toggle();
        }
    }

    public void setValues()
    {
        // Killaura
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).autoblock.setValue(true);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).aps.setValue(16);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).reach.setValue(6.0);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).sdelay.setValue(6);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).FOV.setValue(360);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).ticks.setValue(40);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).players.setValue(true);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).animals.setValue(false);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).monsters.setValue(false);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).friends.setValue(false);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).invisibles.setValue(false);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).death.setValue(true);
        ((Killaura) Client.INSTANCE.getCheatManager().get(Killaura.class)).lockView.setValue(false);
        // Knockback
        ((Velocity) Client.INSTANCE.getCheatManager().get(Velocity.class)).mode.setValue(Velocity.Mode.NORMAL);
        // Step
        ((Step) Client.INSTANCE.getCheatManager().get(Step.class)).height.setValue(5.0);
        // AntiBot
        ((AntiBot) Client.INSTANCE.getCheatManager().get(AntiBot.class)).death.setValue(true);
    }

    public void addBlacklists()
    {
    }
}
