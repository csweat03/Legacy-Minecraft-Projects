package club.shmoke.main.api.check;

import club.shmoke.main.api.helper.IBukkit;
import club.shmoke.main.api.helper.PrintHelper;
import club.shmoke.main.api.helper.User;
import club.shmoke.main.exocheat.bot.BotManager;
import club.shmoke.main.exocheat.ExoCheat;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;
import java.util.UUID;

/**
 * @author Christian
 */
public abstract class Check implements Listener, IBukkit, PrintHelper {

    private String label;
    private boolean active, silent, bannable, debug;
    private int alertTillBan;
    public static Player player;

    protected abstract void onMove(Player player, PlayerMoveEvent event);

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public boolean isBannable() {
        return bannable;
    }

    public void setBannable(boolean bannable) {
        this.bannable = bannable;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getAlertsTillBan() {
        return alertTillBan;
    }

    public void setAlertTillBan(int alertTillBan) {
        this.alertTillBan = alertTillBan;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        player = event.getPlayer();
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL || !isActive()) return;
        Map<UUID, User> map = ExoCheat.get().getUsers();
        if (!map.containsKey(event.getPlayer().getUniqueId()))
            map.put(player.getUniqueId(), new User(player, new BotManager(UUID.randomUUID(), player.getLocation(), player)));
        if (map.containsKey(event.getPlayer().getUniqueId()))
            onMove(map.get(player.getUniqueId()).getPlayer(), event);
    }

    protected boolean isNormal = ExoCheat.get().getLevel() == ExoCheat.Level.NORMAL;
    protected boolean isStrict = ExoCheat.get().getLevel() == ExoCheat.Level.STRICT;
    protected boolean isLenient = ExoCheat.get().getLevel() == ExoCheat.Level.LENIENT;

}
