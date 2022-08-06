package club.shmoke.main.api.command;

import club.shmoke.main.api.helper.PrintHelper;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command implements CommandExecutor, PrintHelper {
    private String syntax;

    private CommandSender sender;
    private Player player;

    public Command(String syntax) {
        this.syntax = syntax;
    }

    public String getSyntax() {
        return syntax;
    }

    public abstract void onCommand(String[] args);

    public CommandSender getSender() {
        return sender;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        sender = commandSender;
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            this.player = player;
        }
        onCommand(args);
        return true;
    }
}
