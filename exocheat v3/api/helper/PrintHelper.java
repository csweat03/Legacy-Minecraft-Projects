package club.shmoke.main.api.helper;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public interface PrintHelper {

    default void print(Object str) {
        String string = str + "";
        for (C c: C.values())
            string.replace(c.name(), c.getCode());
        Bukkit.getConsoleSender().sendMessage(string);
    }
    default void message(CommandSender player, Object str) {
        String string = str + "";
        for (C c: C.values())
            string = string.replace(c.toString(), c.getCode());
        player.sendMessage(string);
    }
}
