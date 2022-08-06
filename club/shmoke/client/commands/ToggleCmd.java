package club.shmoke.client.commands;

import java.util.ArrayList;

import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.command.Command;
import club.shmoke.client.util.GameLogger;
/**
 * @author Kyle
 * @since 8/2017
 **/
public class ToggleCmd extends Command
{
	private static ArrayList<String> aliases = new ArrayList();
	
    public ToggleCmd()
    {
        super("toggle", "Toggle modules" , "toggle <cheat>", aliases);
        aliases.add("t");
        aliases.add("enable");
        aliases.add("disable");
        aliases.add("getState");
    }

    @Override
    public void dispatch(String[] args, String message)
    {
        if (args.length != 0)
        {
            Cheat cheat = Client.INSTANCE.getCheatManager().get(args[0]);

            if (cheat != null)
            {
                cheat.toggle();
                GameLogger.log(String.format("%s was toggled %s", cheat.label(), String.format("%s", (cheat.getState() ? "ON" : "OFF"))), false, GameLogger.Type.INFO);
            }
            else
            {
                GameLogger.log("Cheat Unknown!", false, GameLogger.Type.ERROR);
            }
        }
        else
        {
            GameLogger.log(syntaxMsg(), false, GameLogger.Type.ERROR);
        }
    }
}
