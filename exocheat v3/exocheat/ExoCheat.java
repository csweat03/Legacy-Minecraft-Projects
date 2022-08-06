package club.shmoke.main.exocheat;

import club.shmoke.main.API;
import club.shmoke.main.Plugin;
import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.check.CheckManager;
import club.shmoke.main.api.command.Command;
import club.shmoke.main.api.command.CommandManager;
import club.shmoke.main.api.helper.PrintHelper;
import club.shmoke.main.api.helper.User;
import club.shmoke.main.api.listener.Listener;
import club.shmoke.main.api.listener.ListenerManager;
import club.shmoke.main.exocheat.bot.BotManager;
import club.shmoke.main.exocheat.events.BanEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Christian
 */
public class ExoCheat extends Plugin implements PrintHelper {

    private static ExoCheat exoCheat;
    private final CommandManager COMMAND_MANAGER = new CommandManager();
    private final CheckManager CHECK_MANAGER = new CheckManager();
    private final ListenerManager LISTENER_MANAGER = new ListenerManager();
    private final Map<UUID, User> USERS = new HashMap<>();

    private boolean enabled = true;
    private Level level;
    private String[] links = {"https://discord.gg/EhXJHPA", "https://shmoke.club"};

    private API api = new API(this);

    public static ExoCheat get() {
        return exoCheat;
    }

    public void onStartup() {
        level = Level.NORMAL;
        load();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : AlertManager.GET.getBanwave()) {
                BanEvent event = new BanEvent();
                if (!event.isCancelled()) {
                    player.kickPlayer("\247c\247lYou have been detected for the use of illegal client modifications!\n\2477Your ban will expire: \2476Never\n\n\2477You may appeal below: \n\2478Discord: \2477" + links[0] + "\n\2478Website: \2477" + links[1]);
                    AlertManager.GET.getBanwave().remove(player);
                }
            }
            // Note: these are marked as 'TPS' in other words
            // for every second you want to add 20 to these number.
        }, 0, 3600);
    }

    public void onShutdown() {

    }

    public API API() {
        return api;
    }

    public Map<UUID, User> getUsers() {
        return USERS;
    }

    private void load() {
        exoCheat = this;
        COMMAND_MANAGER.load();
        CHECK_MANAGER.load();
        LISTENER_MANAGER.load();

        for (Check check : CHECK_MANAGER.getContents())
            Bukkit.getPluginManager().registerEvents(check, this);
        for (Command command : COMMAND_MANAGER.getContents())
            getCommand(command.getSyntax()).setExecutor(command);
        for (Listener listener : LISTENER_MANAGER.getContents())
            Bukkit.getPluginManager().registerEvents(listener, this);
        for (Player player : Bukkit.getOnlinePlayers())
            USERS.put(player.getUniqueId(), new User(player, new BotManager(UUID.randomUUID(), player.getLocation(), player)));
    }

    public CommandManager getCommandManager() {
        return COMMAND_MANAGER;
    }

    public CheckManager getCheckManager() {
        return CHECK_MANAGER;
    }

    public ListenerManager getListenerManager() {
        return LISTENER_MANAGER;
    }

    public boolean getAnticheatStatus() {
        return enabled;
    }

    public void setAnticheatStatus(boolean enabled) {
        this.enabled = enabled;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public enum Level {
        STRICT("False flags quit a bit. Blocks all suspicious activity."),
        NORMAL("Blocks majority of suspicious activity, false flags less."),
        LENIENT("Only picks up on very blatant cheats, never false flags.");

        private String description;

        Level(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }


    }
}
