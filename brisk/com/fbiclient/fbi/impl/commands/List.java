package com.fbiclient.fbi.impl.commands;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

import me.xx.api.command.Command;
import me.xx.api.command.CommandManifest;
import com.fbiclient.fbi.impl.Brisk;

@CommandManifest(label = "List", handles = { "help", "cmds", "commands" })
public class List extends Command {

	private Collator collator = Collator.getInstance(Locale.US);

	@Override
	public void dispatch(String[] args, String input) {
		ArrayList<Command> cmds = new ArrayList<>();
		String message = "";
		try {
			message = args[1];
		} catch (Exception e) {
		}
		logCommands(cmds);
	}

	private void logCommands(ArrayList<Command> cmds) {
		for (Command cmd : Brisk.INSTANCE.getCommandManager().getContent()) {
			if (!cmds.contains(cmd))
				cmds.add(cmd);
		}
		cmds.sort((cmd1, cmd2) -> {
			return collator.compare(cmd1.getLabel(), cmd2.getLabel());
		});
		StringBuilder listcmds = new StringBuilder("\247fCommands: ");
		for (Command cmd : cmds) {
			listcmds.append("\2477").append(cmd.getLabel()).append(" \2478" + Brisk.INSTANCE.getCommandManager().getPrefix() + " ");
		}
		this.clientChatMsg().appendText(listcmds.toString().substring(0, listcmds.toString().length() - 2)).send();
	}

}
