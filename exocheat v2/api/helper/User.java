package club.shmoke.main.api.helper;

import club.shmoke.main.exocheat.bot.BotManager;
import org.bukkit.entity.Player;

/**
 * @author Christian
 */
public class User {

    private Player player;
    private BotManager[] bot;

    public User(Player player, BotManager... bots) {
        this.player = player;
        this.bot = bots;
    }

    public Player getPlayer() {
        return player;
    }

    public BotManager[] getBot() {
        return bot;
    }
}
