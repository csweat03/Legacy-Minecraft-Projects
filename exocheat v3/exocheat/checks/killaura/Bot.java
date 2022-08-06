package club.shmoke.main.exocheat.checks.killaura;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.helper.User;
import club.shmoke.main.exocheat.bot.BotManager;
import club.shmoke.main.api.helper.TimeHelper;
import club.shmoke.main.exocheat.ExoCheat;
import club.shmoke.main.exocheat.checks.Killaura;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Christian
 */
public class Bot {

    private static BotManager bot;

    public static void onCall(Event event) {
        PlayerMoveEvent moveEvent = (PlayerMoveEvent) event;

        Player player = moveEvent.getPlayer();
        Map<UUID, User> uList = ExoCheat.get().getUsers();

        if (uList.containsKey(player.getUniqueId()) && uList.get(player.getUniqueId()).getBot() != null && bot != null && bot.getEntityBot() != null && bot.getBot() != null) {
            bot.destroy();
            bot = null;
        } else {
            uList.put(player.getUniqueId(), new User(player, new BotManager(UUID.randomUUID(), player.getLocation(), player)));
            spawnEntity(player);
        }
    }

    private static void spawnEntity(Player player) {
        bot = ExoCheat.get().getUsers().get(player.getUniqueId()).getBot();
        bot.create();
    }
}
