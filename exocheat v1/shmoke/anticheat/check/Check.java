package club.shmoke.anticheat.check;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.helper.IBukkit;
import club.shmoke.anticheat.helper.TimeHelper;
import org.bukkit.GameMode;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public abstract class Check implements Listener, IBukkit {
    private boolean silent, active, bannable, debug;
    private String label, id;
    private Integer leniency;
    public PlayerMoveEvent playerMoveEvent;
    public BlockPlaceEvent blockPlaceEvent;

    public Check(String label) {
        this.label = label;
        this.id = label.replace(" ", "").replace("[", "").replace("]", "").toLowerCase();
        FileConfiguration cfg = Anticheat.anticheat.getConfig();
        if (!cfg.contains(label)) {
            this.silent = true;
            this.active = true;
            this.bannable = false;
            this.debug = false;
        } else {
            this.silent = (boolean) cfg.get(label + ".silent");
            this.active = (boolean) cfg.get(label + ".active");
            this.bannable = (boolean) cfg.get(label + ".bannable");
            //this.leniency = (int) cfg.get(label + ".leniency");
        }
        this.leniency = 30;
    }

    public Integer getLeniency() {
        return leniency;
    }

    public void setLeniency(Integer leniency) {
        this.leniency = leniency;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
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

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setBannable(boolean bannable) {
        this.bannable = bannable;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract void onMove();
    public abstract void onPlace();

    @EventHandler
    public void movable(PlayerMoveEvent event) {
        setPlayer(event);
        playerMoveEvent = event;
        if (!isActive() || getPlayer() != event.getPlayer() || getPlayer().getGameMode() == GameMode.CREATIVE || getPlayer().getGameMode() == GameMode.SPECTATOR || getPlayer().getAllowFlight())
            return;
        onMove();
    }

    @EventHandler
    public void placable(BlockPlaceEvent event) {
        blockPlaceEvent = event;
        if (!isActive() || getPlayer() != event.getPlayer() || getPlayer().getGameMode().equals(GameMode.CREATIVE) || getPlayer().getGameMode().equals(GameMode.SPECTATOR))
            return;
        onPlace();
    }
}
