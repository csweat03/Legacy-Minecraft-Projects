package club.shmoke.client.commands;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.cheats.visual.ClickGui;
import club.shmoke.client.cheats.visual.Overlay;
import club.shmoke.api.command.Command;
import club.shmoke.client.util.GameLogger;

/**
 * @author Kyle
 * @since 8/2017
 **/
public class BindCmd extends Command {

	private static ArrayList<String> aliases = new ArrayList<>();

	public BindCmd() {
		super("Bind", "Bind modules to macro keys", "bind <cheat> [macro] | clear", aliases);
		aliases.add("macro");
		aliases.add("b");
		aliases.add("m");
	}

	@Override
	public void dispatch(String[] args, String message) {
		if (args.length == 2) {
			for (Cheat c : Client.INSTANCE.getCheatManager().getContents()) {
				if (args[0].equalsIgnoreCase(c.id)) {
					int macro = Keyboard.getKeyIndex(args[1].toUpperCase());
					c.setMacro(macro);
					GameLogger.log(String.format("%s has been bound to %s", c.label(), Keyboard.getKeyName(macro)),
							GameLogger.Type.INFO);
				}
			}
		} else if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
			for (Cheat cheat : Client.INSTANCE.getCheatManager().getContents()) {
				if (cheat.equals(Client.INSTANCE.getCheatManager().get(ClickGui.class))
						|| cheat.equals(Client.INSTANCE.getCheatManager().get(Overlay.class)))
					continue;
				cheat.setMacro(0);
			}
			GameLogger.log("Cheat keybinds have been cleared!", false, GameLogger.Type.INFO);
		} else {
			GameLogger.log(syntaxMsg(), false, GameLogger.Type.ERROR);
		}
	}
}
