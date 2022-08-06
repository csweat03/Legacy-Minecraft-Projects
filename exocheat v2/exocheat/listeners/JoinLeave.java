package club.shmoke.main.exocheat.listeners;

import club.shmoke.main.exocheat.bot.BotManager;
import club.shmoke.main.api.helper.User;
import club.shmoke.main.api.listener.Listener;
import club.shmoke.main.exocheat.ExoCheat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

/**
 * @author Christian
 */
public class JoinLeave extends Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        ExoCheat.get().getUsers().put(p.getUniqueId(), new User(p, new BotManager(UUID.randomUUID(), p.getLocation(), p)));

        BotManager[] bot = ExoCheat.get().getUsers().get(p.getUniqueId()).getBot();

        for (BotManager botManager : bot)
            botManager.create();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        ExoCheat.get().getUsers().remove(p.getUniqueId());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Map<UUID, User> uList = ExoCheat.get().getUsers();
        if (!uList.containsKey(player.getUniqueId())) {
            uList.put(player.getUniqueId(), new User(player, new BotManager(UUID.randomUUID(), player.getLocation(), player)));
            for (BotManager botManager : uList.get(player.getUniqueId()).getBot())
                botManager.create();

        }
        BotManager[] bot = uList.get(player.getUniqueId()).getBot();

        for (BotManager botManager : bot)
            botManager.setLocation(player.getLocation());

    }

}
