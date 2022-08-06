package com.fbiclient.fbi.impl.commands.cheats;

import me.xx.api.cheat.Cheat;
import me.xx.api.command.Command;
import me.xx.api.command.CommandManifest;
import com.fbiclient.fbi.impl.Brisk;
import me.xx.utility.chat.ChatColor;

@CommandManifest(label = "Visible", handles = { "Vis", "V" })
public class Visible extends Command
{
    @Override
    public void dispatch(String[] arguments, String raw) {
        if (arguments.length != 1 && arguments.length != 2) {
            this.clientChatMsg().appendText("Invalid arguments, please enter cheat name", new ChatColor[0]).send();
            return;
        }
        Cheat module = Brisk.INSTANCE.getCheatManager().lookup(arguments[0]);
        if (module == null) {
            this.clientChatMsg().appendText("Cheat ", new ChatColor[0]).appendText(arguments[0], ChatColor.GRAY).appendText(" is not found!", new ChatColor[0]).send();
            return;
        }
        if (arguments.length == 2) {
            if (arguments[1].equalsIgnoreCase("true") || arguments[1].equalsIgnoreCase("false")) {
                module.setVisible(arguments[1].equalsIgnoreCase("true"));
                this.clientChatMsg().appendText("Set ", new ChatColor[0]).appendText(module.getLabel() + "'s ", ChatColor.GRAY).appendText("visibility to ", new ChatColor[0]).appendText(module.isVisible() + "", ChatColor.GRAY).send();
            }
            else {
                this.clientChatMsg().appendText("Invalid arguments, enter boolean true/false", new ChatColor[0]).send();
            }
        }
        else {
            this.clientChatMsg().appendText(module.getLabel() + "'s ", ChatColor.GRAY).appendText("visibility is ", new ChatColor[0]).appendText(module.isVisible() + "", ChatColor.GRAY).send();
        }
    }
}
