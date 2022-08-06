package com.fbiclient.bureau.main.commands;

import com.rylinaux.plugman.util.PluginUtil;
import com.fbiclient.bureau.Bureau;
import com.fbiclient.bureau.BureauData;
import com.fbiclient.bureau.api.command.Command;
import com.fbiclient.bureau.api.command.CommandManifest;
import org.bukkit.command.CommandSender;

@CommandManifest(label = "Restart", syntax = "/restart", triggers = {"restart", "res"})
public class RestartCommand extends Command {

    @Override
    protected void onTrigger(String label, String[] args, CommandSender sender) {
        if (args.length != 0) return;

        PluginUtil.reload(Bureau.getPlugin());
        sender.sendMessage("\247aYou have successfully restarted \2476\247l" + BureauData.getName() + "\247r\247a!");
    }
}
