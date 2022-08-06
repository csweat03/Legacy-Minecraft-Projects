package club.shmoke.api.command;

import java.io.IOException;

import club.shmoke.client.commands.*;
import club.shmoke.client.util.GameLogger;
import club.shmoke.client.util.manage.ListManager;

/**
 * @author Kyle
 * @since 8/2017
 **/
public class CommandManager extends ListManager<Command> {
	public void registerCommands() throws IOException {
		include(new BindCmd(), new PresetCmd(), new FriendCmd(), new IGNCmd(), new ToggleCmd(), new MusicCmd(), new ModuleCmd());
	}

	@Override
	public void setup() {
		GameLogger.log("Loading Horizon... 16% Complete", false, GameLogger.Type.SYSTEM);
		try {
			registerCommands();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			for(Command c: getContents())
				c.setLabel(c.label);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GameLogger.log("Loading Horizon... 32% Complete", false, GameLogger.Type.SYSTEM);
	}

	public boolean runCommandMessage(String message) {
		if (!message.startsWith(".")) {
			return false;
		}

		for (Command c : getContents()) {
			String rest = message.substring(1);
			String name = rest.split(" ")[0];

			for (String alias : c.aliases()) {
				if (alias.equalsIgnoreCase(name) || c.label().equalsIgnoreCase(name)) {
					String[] oldArgs = rest.split(" ");
					String[] newArgs = new String[oldArgs.length - 1];
					int i = 0;

					for (String a : oldArgs) {
						if (i != 0) {
							newArgs[i - 1] = a;
						}

						i++;
					}

					c.dispatch(newArgs, message);
					return true;
				}
			}
		}

		GameLogger.log("Command Unknown!", false, GameLogger.Type.ERROR);
		return true;
	}
}
