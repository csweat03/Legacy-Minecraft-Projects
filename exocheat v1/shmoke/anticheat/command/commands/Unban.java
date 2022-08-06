package club.shmoke.anticheat.command.commands;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 * @author Christian
 */
public class Unban extends Command {

    public Unban() {
        super ("unban");
    }

    @Override
    public void onCommand(String label, String[] args) {
        if (args.length == 1){
            for (OfflinePlayer player: Bukkit.getOfflinePlayers()) {
                if (args[0].equalsIgnoreCase(player.getName()) && Anticheat.anticheat.getConfig().contains(player.getUniqueId().toString())) {
                    Anticheat.anticheat.getConfig().set(player.getUniqueId().toString(), null);
                    Anticheat.anticheat.saveConfig();
                    getSender().sendMessage(player.getName() + " has been unbanned!");
                    return;
                } else {
                    getSender().sendMessage("\247cPlayer does not exist or is not banned!");
                    return;
                }
            }
        }else
            getSender().sendMessage("/unban [name]");
    }
}
