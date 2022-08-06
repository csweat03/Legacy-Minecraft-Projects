package com.fbiclient.fbi.impl.commands.cheats;

import me.xx.api.cheat.Cheat;
import me.xx.api.command.Command;
import me.xx.api.command.CommandManifest;
import com.fbiclient.fbi.impl.Brisk;
import me.xx.utility.chat.ChatColor;

@CommandManifest(label = "Toggle", handles = { "T" })
public class Toggle extends Command {
	
	@Override
	public void dispatch(String[] arguments, String raw) {
		if (arguments.length != 1) {
			this.clientChatMsg().appendText("Cheat " + arguments[0] + " not found!", new ChatColor[0])
					.send();
			return;
		}
		Cheat cheat = Brisk.INSTANCE.getCheatManager().lookup(arguments[0]);
		if (cheat == null) {
			this.clientChatMsg().appendText("Cheat not found!", new ChatColor[0])
					.send();
			return;
		}
		cheat.toggle();
		this.clientChatMsg().appendText("Toggled ", new ChatColor[0])
				.appendText(cheat.getLabel(), ChatColor.GRAY)
				.appendText(cheat.getState() ? " ON" : " OFF", new ChatColor[0]).send();
	}
}
