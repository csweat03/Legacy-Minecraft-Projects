package com.fbiclient.fbi.impl.commands;

import me.xx.api.command.Command;
import me.xx.api.command.CommandManifest;
import com.fbiclient.fbi.impl.Brisk;

@CommandManifest(label = "Prefix")
public class Prefix extends Command {
	
	@Override
	public void dispatch(String[] arguments, String raw) {
		if (arguments.length == 1) {
			if (arguments[0].length() == 1) {
				Brisk.INSTANCE.getCommandManager().setPrefix(arguments[0].charAt(0));
				this.clientChatMsg().appendTextF("Prefix has been set to \2477%s\247f", arguments[0]).send();
				Brisk.INSTANCE.getCommandManager().save();
			}
		} else {
			this.clientChatMsg().appendTextF("Prefix is \2477%s\247f",
					Brisk.INSTANCE.getCommandManager().getPrefix());
		}
	}
}
