package club.shmoke.client.commands;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import club.shmoke.client.Client;
import club.shmoke.api.command.Command;
import club.shmoke.client.util.GameLogger;

/**
 * @author Kyle
 * @since 10/26/2017
 **/
public class FriendCmd extends Command {
	private static ArrayList<String> aliases = new ArrayList();

	
	public FriendCmd() {
		super("friend", "Add and remove friends from friend list", "friend [username]", aliases);
		aliases.add("f");
		aliases.add("friends");
		aliases.add("neutral");
	}

	@Override
	public void dispatch(String[] args, String message) {
		if (args.length == 1) {
			for (Entity p : Minecraft.getMinecraft().theWorld.loadedEntityList) {
				if (args[0].equalsIgnoreCase(p.getName())) {
					if (Client.INSTANCE.getFriendManager().has(p.getName())) {
						GameLogger.log("You have removed " + p.getName() + "!", false, GameLogger.Type.INFO);
						Client.INSTANCE.getFriendManager().remove(p.getName());
					} else {
						GameLogger.log("You have added " + p.getName() + "!", false, GameLogger.Type.INFO);
						Client.INSTANCE.getFriendManager().add(p.getName(), p.getName());
					}
				}
			}
		} else {
			GameLogger.log(syntaxMsg(), false, GameLogger.Type.ERROR);
		}
	}
}
