package club.shmoke.api.command;

import club.shmoke.api.event.EventHandler;
import club.shmoke.api.utility.utilities.Logger;
import club.shmoke.api.utility.utilities.ManagerUtility;
import club.shmoke.main.commands.BindCmd;
import club.shmoke.main.commands.FriendCmd;
import club.shmoke.main.events.ChatEvent;
import net.minecraft.client.Minecraft;

public class CommandManager extends ManagerUtility<Command> {

    public void initialize() {
        addContent(new BindCmd(), new FriendCmd());
    }

    public final String getPrefix() {
        return ".";
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        if (event.getType() != ChatEvent.Type.SEND || !event.getMessage().startsWith(getPrefix())) return;

        event.cancel();

        getContents().forEach(command -> {
            String parsed = event.getMessage().substring(1);
            String label = parsed.split(" ")[0];

            for (String alias : command.getAlias()) {
                if (alias.equalsIgnoreCase(label) || command.getLabel().equalsIgnoreCase(label)) {
                    String[] oldArgs = parsed.split(" "), newArgs = new String[oldArgs.length - 1];
                    int i = 0;

                    for (String a : oldArgs) {
                        if (i != 0)
                            newArgs[i - 1] = a;
                        i++;
                    }

                    try {
                        command.dispatch(newArgs, event.getMessage());
                    } catch (Exception ex) {
                        Logger.log("\247cInvalid Syntax! \2477Try: " + command.getSyntax(), Logger.Level.ERROR);
                    }
                    return;
                }
            }
        });
    }
}
