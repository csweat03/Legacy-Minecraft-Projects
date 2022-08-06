package club.shmoke.anticheat;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.check.CheckManager;
import club.shmoke.anticheat.command.Command;
import club.shmoke.anticheat.command.CommandManager;
import club.shmoke.anticheat.helper.AlertManager;
import club.shmoke.anticheat.helper.MathHelper;
import club.shmoke.anticheat.helper.User;
import club.shmoke.anticheat.helper.PrintHelper;
import club.shmoke.anticheat.listeners.MainListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class Anticheat extends JavaPlugin implements org.bukkit.event.Listener, PrintHelper {
    public static Anticheat anticheat = null;
    public final HashMap<UUID, User> players = new HashMap<>();
    public final CheckManager checkManager = new CheckManager();
    public final CommandManager commandManager = new CommandManager();
    public final AlertManager alertManager = new AlertManager();
    public final MathHelper math = new MathHelper();
    public String name;
    public String prefix;
    public String banReason;

    public void onEnable() {
        anticheat = this;
        print("\n\n\247eAnticheat \247rhas been\247a enabled!\n\n");
        resetVio();
        checkManager.registerChecks();
        commandManager.registerCommands();
        setupConfig();
        for (Check c : checkManager.getChecks())
            getServer().getPluginManager().registerEvents(c, this);
        for (Command c: commandManager.getCommands())
            getCommand(c.getSyntax()).setExecutor(c);
        getServer().getPluginManager().registerEvents(new MainListener(), this);
        for (Player p : Bukkit.getOnlinePlayers())
            players.put(p.getUniqueId(), new User(p));
    }

    public void onDisable() {
        print("\n\n\247eAnticheat \247rhas been\247c disabled!\n\n");
    }

    private void resetVio() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            Iterator iter = Bukkit.getOnlinePlayers().iterator();

            while (iter.hasNext()) {
                Player all = (Player) iter.next();
                if (Anticheat.anticheat.alertManager.getViolationLevel(all) != 0) {
                    for (Check c : Anticheat.anticheat.checkManager.getChecks())
                        Anticheat.anticheat.alertManager.resetViolations(0, all, c);
                    if (!all.hasPermission(AlertManager.Permissions.ADMIN.toString())) continue;
                    all.sendMessage(prefix + "\247aAll violations have been reset.");
                }
            }
        }, 0L, 20L * 60);
    }

    private void setupConfig() {
        loadConfig();
        addValue("name", "ExoCheat");
        addValue("prefix", "&8&l[&c&lExo&7Cheat&8&l] &r");
        addValue("reason", "&cYou have been caught cheating.\nYou are permanently suspended from this server.\nYou can appeal at https://shmoke.club");
        for (Check c: checkManager.getChecks()) {
            if (!getConfig().contains(c.getLabel())) {
                Map<String, Object> status = new HashMap<>();
                status.put("active", c.isActive());
                status.put("silent", c.isSilent());
                status.put("bannable", c.isBannable());
                status.put("debug", c.isDebug());
                //status.put("leniency", c.getLeniency());
                getConfig().set(c.getLabel(), status);
                saveConfig();
            }
        }
        name = getConfig().get("name").toString().replace("&", "\247");
        prefix = getConfig().get("prefix").toString().replace("&", "\247");
        banReason = getConfig().get("reason").toString().replace("&", "\247");

    }

    private void addValue(String path, String data) {
        if (!getConfig().contains(path)) {
            getConfig().set(path, data);
            saveConfig();
        }
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
