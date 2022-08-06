package club.shmoke.client.commands;

import java.util.ArrayList;

import club.shmoke.client.Client;
import club.shmoke.api.command.Command;
import club.shmoke.client.preset.Config;
import club.shmoke.client.util.GameLogger;

public class PresetCmd extends Command {
	
	private static ArrayList<String> aliases = new ArrayList();

	public PresetCmd() {
		super("Preset", "Allows you to load saved preset.", ".preset load (preset)", aliases);
		aliases.add("pres");
		aliases.add("p");
		aliases.add("config");
		aliases.add("c");
	}
	
	@Override
    public void dispatch(String[] args, String message)
    {
		if (args.length == 2)
        {
            if(args[0].equalsIgnoreCase("load")) {
                Config config = Client.INSTANCE.getConfigManager().find(args[1]);
            	if (config != null)
                {
                    config.setEnabled();
                    GameLogger.log(String.format("%s config has been toggled!", config.label()), false, GameLogger.Type.INFO);
                }
                else
                {
                    GameLogger.log("Config Unknown!", false, GameLogger.Type.ERROR);
                }
            }
        }
        else
        {
            GameLogger.log(syntaxMsg(), false, GameLogger.Type.ERROR);
        }
    }

}
