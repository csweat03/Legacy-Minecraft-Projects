package club.shmoke.anticheat.helper;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Christian
 */
public interface PrintHelper {

    default void print(Object str) {
        String string = str + "";
        Bukkit.getConsoleSender().sendMessage(string);
    }
    default void message(CommandSender player, Object str) {
        String string = str + "";
        player.sendMessage(string);
    }

}
