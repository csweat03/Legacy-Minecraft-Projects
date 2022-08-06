package com.fbiclient.fbi.impl.commands;

import me.xx.api.command.Command;
import me.xx.api.command.CommandManifest;
import me.xx.api.profile.Profile;
import com.fbiclient.fbi.impl.Brisk;

/**
 * @author Kyle
 * @since 3/22/2018
 **/
@CommandManifest(label = "Config", handles = {"con", "preset", "profile"})
public class Configs extends Command {

    @Override
    public void dispatch(String[] arguments, String input) {
        if (arguments.length == 1) {
            Profile config = Brisk.INSTANCE.getConfigManager().lookup(arguments[0]);
            Brisk.INSTANCE.getConfigManager().getHandler().setCurrentConfig(config);
            this.clientChatMsg().appendTextF("Config set to \2477%s\247f",
                    Brisk.INSTANCE.getConfigManager().getHandler().getCurrentConfig().getLabel()).send();
        } else {
            this.clientChatMsg().appendTextF("Current profile is \2477%s\247f",
                    Brisk.INSTANCE.getConfigManager().getHandler().getCurrentConfig().getLabel()).send();
        }
    }
}
