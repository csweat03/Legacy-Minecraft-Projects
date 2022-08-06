package com.fbiclient.fbi.impl.commands.cheats;

import org.lwjgl.input.Keyboard;

import me.xx.api.cheat.Cheat;
import me.xx.api.command.Command;
import me.xx.api.command.CommandManifest;
import com.fbiclient.fbi.impl.Brisk;
import me.xx.utility.chat.ChatColor;

@CommandManifest(label = "Bind", handles = {"B", "Key", "Macro", "Mac", "K", "M"})
public class Bind extends Command {

    @Override
    public void dispatch(String[] arguments, String raw) {
        if (arguments.length < 1) {
            this.clientChatMsg().appendText("Invalid arguments, enter cheat name and key, or clear", new ChatColor[0]).send();
            return;
        }
        if(arguments.length < 2) {
            if(arguments[0] == "clear") {
                for(Cheat c : Brisk.INSTANCE.getCheatManager().getValues()) {
                    c.setKey(0);
                }
                this.clientChatMsg().appendText("All cheats bind's have been cleared!", ChatColor.GRAY)
                        .send();
            }
        }
        if (arguments.length == 2) {
            Cheat cheat = Brisk.INSTANCE.getCheatManager().lookup(arguments[0]);
            if (cheat == null) {
                this.clientChatMsg().appendText("Cheat not found!", new ChatColor[0]).send();
                return;
            }
            cheat.setKey(Keyboard.getKeyIndex(arguments[1].toUpperCase()));
            this.clientChatMsg().appendText(cheat.getLabel() + "'s", ChatColor.GRAY)
                    .appendText(" key set to ", new ChatColor[0]).appendText(arguments[1].toUpperCase(), ChatColor.GRAY)
                    .send();
        }
    }
}
