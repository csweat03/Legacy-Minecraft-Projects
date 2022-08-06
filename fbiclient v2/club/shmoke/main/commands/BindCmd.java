package club.shmoke.main.commands;

import club.shmoke.Client;
import club.shmoke.api.command.Command;
import club.shmoke.api.utility.utilities.Logger;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

public class BindCmd extends Command {

    public BindCmd() {
        super("Bind", ".bind <cheat> <key>", new String[]{"bind"});
    }

    @Override
    public void dispatch(@NotNull String[] args, @NotNull String message) {
        Client.GET.CHEAT_MANAGER.getContents().forEach(cheat -> {
            if (args[0].equalsIgnoreCase(cheat.getLabel())) {
                cheat.setKey(Keyboard.getKeyIndex(args[1].toUpperCase()));
                Logger.log("\247c" + cheat.getLabel() + "'s \2477keybind has been updated! \247cNew keybind: \2477" + Keyboard.getKeyName(cheat.getKey()), Logger.Level.INFO);
            }
        });
    }
}
