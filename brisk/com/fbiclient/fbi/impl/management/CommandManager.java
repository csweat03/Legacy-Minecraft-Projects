package com.fbiclient.fbi.impl.management;

import com.google.gson.*;

import me.xx.api.command.Command;
import me.xx.api.event.EventManager;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.player.ChatEvent;
import com.fbiclient.fbi.client.framework.helper.IChatBuilder;
import com.fbiclient.fbi.client.management.types.ArrayListManager;
import com.fbiclient.fbi.impl.commands.Configs;
import com.fbiclient.fbi.impl.commands.Friend;
import com.fbiclient.fbi.impl.commands.Prefix;
import com.fbiclient.fbi.impl.commands.cheats.Bind;
import com.fbiclient.fbi.impl.commands.cheats.Toggle;
import com.fbiclient.fbi.impl.commands.cheats.Visible;
import me.xx.utility.chat.ChatColor;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandManager extends ArrayListManager<Command> implements IChatBuilder {

	private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private char prefix;
	private File commandFile;

	public CommandManager(File gameDirectory) {
		super.setup();
		this.prefix = '.';
		EventManager.INSTANCE.register(this);
		this.commandFile = new File(gameDirectory + File.separator + "prefix.json");
		register(new com.fbiclient.fbi.impl.commands.List(),
				new Bind(), 
				new Toggle(), 
				new Friend(),
				new Prefix(),
				new Configs(),
				new Visible());
		try {
			if (!this.commandFile.exists()) {
				this.commandFile.createNewFile();
				this.save();
				return;
			}
			this.load();
		} catch (IOException exception) {
			System.exit(0);
		}
	}

	private void publish(String message) {
		message = message.substring(1);
		String[] arguments = message.split("\\s+");
		if (arguments.length == 0) {
			this.clientChatMsg().appendText("Please enter a valid command", new ChatColor[0]).send();
			return;
		}
		Optional<Command> command = this.get(arguments[0]);
		if (!command.isPresent()) {
			this.clientChatMsg().appendText("Command", new ChatColor[0])
					.appendText(String.format(" '%s' ", arguments[0]), ChatColor.GRAY)
					.appendText("is invalid", new ChatColor[0]).send();
			return;
		}
		try {
			command.get().dispatch(Arrays.copyOfRange(arguments, 1, arguments.length), message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean valid(String message) {
		return !message.isEmpty() && message.toCharArray()[0] == this.prefix;
	}

	public Optional<Command> get(String label) {
		return getRegistry().stream().filter(command -> command.matches(label)).findFirst();
	}

	@Register
	public void handleChat(ChatEvent event) {
		if (!this.valid(event.getMessage())) {
			return;
		}
		this.publish(event.getMessage());
		event.setCancelled(true);
	}

	public char getPrefix() {
		return this.prefix;
	}

	public void setPrefix(char prefix) {
		this.prefix = prefix;
	}

	public List<Command> getContent() {
		return this.getRegistry();
	}

	private void load() {
		try {
			this.prefix = new JsonParser().parse((Reader) new FileReader(this.commandFile)).getAsJsonObject()
					.get("prefix").getAsCharacter();
		} catch (IOException ex) {
		}
	}

	public void save() {
		try {
			PrintWriter printWriter = new PrintWriter(this.commandFile);
			JsonObject jsonObject = new JsonObject();
			jsonObject.add("prefix", (JsonElement) new JsonPrimitive(this.prefix));
			printWriter.print(CommandManager.GSON.toJson((JsonElement) jsonObject));
			printWriter.close();
		} catch (IOException ex) {
		} catch (NullPointerException ex2) {
		}
	}

}
