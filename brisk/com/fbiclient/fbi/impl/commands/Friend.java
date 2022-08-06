package com.fbiclient.fbi.impl.commands;

import java.util.Optional;
import java.util.UUID;

import me.xx.api.command.Command;
import me.xx.api.command.CommandManifest;
import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.impl.Brisk;
import me.xx.utility.chat.ChatColor;
import net.minecraft.client.network.NetworkPlayerInfo;

@CommandManifest(label = "Friend", handles = { "f" })
public class Friend extends Command {
	
	@Override
	public void dispatch(String[] arguments, String raw) {
		if (arguments.length < 2 || arguments.length > 3) {
			clientChatMsg().appendText("Invalid arguments, sub-commands and name", new ChatColor[0]).send();
			return;
		}
		String lowerCase = arguments[0].toLowerCase();
		switch (lowerCase) {
		case "a":
		case "add": {
			new AddFriendThread(arguments[1], (arguments.length == 3) ? arguments[2] : arguments[1]).start();
			break;
		}
		case "r":
		case "rem":
		case "delete":
		case "del":
		case "remove": {
			new RemoveFriendThread(arguments[1]).start();
			break;
		}
		}
	}

	class AddFriendThread extends Thread {
		private String name;
		private String nickname;

		AddFriendThread(String name, String nickname) {
			this.name = name;
			this.nickname = nickname;
		}

		@Override
		public void run() {
			if (Brisk.INSTANCE.getFriendManager().get(this.nickname).isPresent()) {
				clientChatMsg().appendText("Player ", new ChatColor[0]).appendText(this.nickname, ChatColor.GRAY)
						.appendText(" is already a friend", new ChatColor[0]).send();
				return;
			}
			UUID uuid = PROFILE_HELPER.getUUID(this.name);
			if (uuid == null) {
				return;
			}
			if (Brisk.INSTANCE.getFriendManager().get(uuid).isPresent()) {
				clientChatMsg().appendText("Player ", new ChatColor[0]).appendText(this.nickname, ChatColor.GRAY)
						.appendText(" is already a friend", new ChatColor[0]).send();
				return;
			}
			if (IHelper.mc.getNetHandler().getPlayerInfo(this.name) != null) {
				NetworkPlayerInfo player = IHelper.mc.getNetHandler().getPlayerInfo(this.name);
				if (Brisk.INSTANCE.getFriendManager().get(player.getGameProfile().getId()).isPresent()) {
					clientChatMsg().appendText("Player ", new ChatColor[0]).appendText(this.nickname, ChatColor.GRAY)
							.appendText(" is already a friend", new ChatColor[0]).send();
					return;
				}
				Brisk.INSTANCE.getFriendManager().add(player.getGameProfile().getId(), this.nickname);
				clientChatMsg().appendText("Added ", new ChatColor[0]).appendText(this.nickname, ChatColor.GRAY)
						.appendText(" as a friend", new ChatColor[0]).send();
			} else {
				clientChatMsg().appendText("Player ", new ChatColor[0]).appendText(this.name, ChatColor.GRAY)
						.appendText(" is not in the server, fetching id...", new ChatColor[0]).send();
				uuid = PROFILE_HELPER.getUUID(this.name);
				if (uuid == null) {
					clientChatMsg().appendText("Player ", new ChatColor[0]).appendText(this.name, ChatColor.GRAY)
							.appendText(" does not exist", new ChatColor[0]).send();
					return;
				}
				if (Brisk.INSTANCE.getFriendManager().get(uuid).isPresent()) {
					clientChatMsg().appendText("Player ", new ChatColor[0]).appendText(this.nickname, ChatColor.GRAY)
							.appendText(" is already a friend", new ChatColor[0]).send();
					return;
				}
				Brisk.INSTANCE.getFriendManager().add(uuid, this.nickname);
				clientChatMsg().appendText("Added ", new ChatColor[0]).appendText(this.nickname, ChatColor.GRAY)
						.appendText(" as a friend", new ChatColor[0]).send();
			}
		}
	}

	class RemoveFriendThread extends Thread {
		private String name;

		RemoveFriendThread(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			if (Brisk.INSTANCE.getFriendManager().get(this.name).isPresent()) {
				Friend friend = (Friend) Brisk.INSTANCE.getFriendManager().get(this.name).get();
				Brisk.INSTANCE.getFriendManager().getFriendList().remove(friend);
				Brisk.INSTANCE.getFriendManager().save();
				clientChatMsg().appendText("Removed ", new ChatColor[0]).appendText(this.name, ChatColor.GRAY)
						.appendText(" as a friend", new ChatColor[0]).send();
				return;
			}
			if (IHelper.mc.getNetHandler().getPlayerInfo(this.name) != null) {
				NetworkPlayerInfo player = IHelper.mc.getNetHandler().getPlayerInfo(this.name);
				Friend friend2 = (Friend) Brisk.INSTANCE.getFriendManager().get(player.getGameProfile().getId())
						.get();
				Brisk.INSTANCE.getFriendManager().getFriendList().remove(friend2);
				Brisk.INSTANCE.getFriendManager().save();
				clientChatMsg().appendText("Removed ", new ChatColor[0]).appendText(this.name, ChatColor.GRAY)
						.appendText(" as a friend", new ChatColor[0]).send();
			} else {
				clientChatMsg().appendText("Player ", new ChatColor[0]).appendText(this.name, ChatColor.GRAY)
						.appendText(" is not in the server, fetching id...", new ChatColor[0]).send();
				UUID uuid = PROFILE_HELPER.getUUID(this.name);
				if (uuid == null) {
					clientChatMsg().appendText("Player ", new ChatColor[0]).appendText(this.name, ChatColor.GRAY)
							.appendText(" does not exist", new ChatColor[0]).send();
					return;
				}
				Optional<com.fbiclient.fbi.client.management.Person> friend3 = Brisk.INSTANCE
						.getFriendManager().get(uuid);
				if (!friend3.isPresent()) {
					clientChatMsg().appendText("Player ", new ChatColor[0]).appendText(this.name, ChatColor.GRAY)
							.appendText(" is not a friend", new ChatColor[0]).send();
					return;
				}
				Brisk.INSTANCE.getFriendManager().getFriendList().remove(friend3.get());
				Brisk.INSTANCE.getFriendManager().save();
				clientChatMsg().appendText("Removed ", new ChatColor[0]).appendText(this.name, ChatColor.GRAY)
						.appendText(" as a friend", new ChatColor[0]).send();
			}
		}
	}
}
