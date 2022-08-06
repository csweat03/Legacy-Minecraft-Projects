package club.shmoke.main.exocheat.checks.killaura;

import club.shmoke.main.api.alert.AlertManager;
import club.shmoke.main.api.helper.RotationHelper;
import club.shmoke.main.api.helper.TimeHelper;
import club.shmoke.main.api.helper.User;
import club.shmoke.main.exocheat.ExoCheat;
import club.shmoke.main.exocheat.bot.BotManager;
import club.shmoke.main.exocheat.checks.Killaura;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Christian
 */
public class Bot {

    private static BotManager bot;
    private static TimeHelper time = new TimeHelper(), resetter = new TimeHelper();
    private static Map<Player, Integer> hits = new HashMap<>();

    public static void onCall(Event event) {
        PlayerMoveEvent moveEvent = (PlayerMoveEvent) event;

        Player player = moveEvent.getPlayer();
        Map<UUID, User> uList = ExoCheat.get().getUsers();

        RotationHelper rotationHelper = new RotationHelper(player);

        if (uList.containsKey(player.getUniqueId()) && uList.get(player.getUniqueId()).getBot() != null && bot != null && bot.getEntityBot() != null && bot.getBot() != null) {
            for (BotManager botManager : uList.get(player.getUniqueId()).getBot()) {
                Player b = botManager.getBot();
                float yaw = Math.abs(rotationHelper.getYaw(b));

                if (yaw <= 10)
                    hits.put(player, hits.getOrDefault(player, 0) + 1);

                if (resetter.hasReached(2000)) {
                    hits.remove(player);
                    resetter.reset();
                }

                if (moveEvent.getTo().getYaw() - moveEvent.getFrom().getYaw() < 50) {
                    if (hits.getOrDefault(player, 0) > 2) {
                        AlertManager.GET.notifyStaff(ExoCheat.get().getCheckManager().get(Killaura.class), event, "Bot", player);
                        hits.remove(player);
                    }
                }

                if (time.hasReached(75)) {
                    bot.destroy();
                    bot = null;
                }
            }
        } else {
            uList.put(player.getUniqueId(), new User(player, new BotManager(UUID.randomUUID(), player.getLocation(), player)));
            spawnEntity(player);
            time.reset();
        }
    }

    private static void spawnEntity(Player player) {
        for (BotManager botManager : ExoCheat.get().getUsers().get(player.getUniqueId()).getBot()) {
            bot = botManager;
            bot.create();
        }
    }
}
