package com.fbiclient.fbi.impl.commands.cheats;

import java.util.Optional;

import me.valkyrie.api.value.Value;
import me.xx.api.cheat.Cheat;
import me.xx.api.command.Command;
import me.xx.api.command.CommandManifest;
import me.xx.utility.chat.ChatBuilder;
import me.xx.utility.chat.ChatColor;

@CommandManifest(label = "", handles = {})
public class Values extends Command {
	private Cheat cheat;

	public Values(Cheat cheat) {
		this.cheat = cheat;
	}

	@Override
	public void dispatch(String[] arguments, String raw) {
		if (arguments.length != 1 && arguments.length != 2) {
			this.clientChatMsg().appendText("Invalid arguments, enter Attribute label", new ChatColor[0]).send();
			return;
		}
		if (arguments[0].equalsIgnoreCase("attribute")) {
			this.clientChatMsg().appendText("Attributes in ", new ChatColor[0])
					.appendText(this.cheat.getLabel(), ChatColor.GRAY).appendText(" includes:", new ChatColor[0])
					.send();
			ChatBuilder chatBuilder = new ChatBuilder();
			this.cheat.getRawValues().forEach(attribute -> chatBuilder
					.appendText(attribute.getFormattedLabel(), ChatColor.GRAY).appendText(", ", new ChatColor[0]));
			chatBuilder.send();
			return;
		}
		Optional<Value> propertyValueOptional = this.cheat.getValue(arguments[0]);
		if (!propertyValueOptional.isPresent()) {
			this.clientChatMsg().appendText("Attribute ", new ChatColor[0])
					.appendText(String.format("\"%s\"", arguments[0]), ChatColor.GRAY)
					.appendText(" does not exist", new ChatColor[0]).send();
			return;
		}
		Value value = propertyValueOptional.get();
		if (arguments.length == 1) {
			this.clientChatMsg().appendText(value.getDisplayLabel() + "'s", ChatColor.GRAY)
					.appendText(" current value is ", new ChatColor[0])
					.appendText("" + value.getValue(), ChatColor.GRAY).send();
		} else {
			try {
				value.fromString(arguments[1]);
				this.clientChatMsg().appendText("Set ", new ChatColor[0])
						.appendText(value.getDisplayLabel() + "'s", ChatColor.GRAY)
						.appendText(" value to ", new ChatColor[0]).appendText("" + value.getValue(), ChatColor.GRAY)
						.send();
				this.cheat.save();
			} catch (IllegalArgumentException exception) {
				this.clientChatMsg().appendText("Invalid input for Attribute", new ChatColor[0]).send();
			}
		}
	}

	@Override
	public boolean matches(String input) {
		return this.cheat.matches(input);
	}
}
