package club.shmoke.main.api.alert;

import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.helper.PrintHelper;
import club.shmoke.main.api.helper.C;
import club.shmoke.main.exocheat.ExoCheat;
import club.shmoke.main.exocheat.events.FlagEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Christian
 */
public enum AlertManager implements PrintHelper {
    GET;

    //private TimeHelper time = new TimeHelper();
    private Map<Check, Integer> trottle = new HashMap<>(), alerts = new HashMap<>();
    private List<Player> banwave = new ArrayList<>();

    public void notifyStaffSilently(Check check, Event event, String reason, Player violator) {
        defaultShit(check, event, reason, violator, false);
    }

    public void notifyStaff(Check check, Event event, String reason, Player violator) {
        defaultShit(check, event, reason, violator, true);
    }

    public void notifyStaff(Check check, Event event, Player violator) {
        notifyStaff(check, event, "", violator);
    }

    private void defaultShit(Check check, Event event, String message, Player violator, boolean flag) {
        FlagEvent flagEvent = new FlagEvent();
        Bukkit.getServer().getPluginManager().callEvent(flagEvent);
        if (flagEvent.isCancelled() || !ExoCheat.get().getAnticheatStatus()) return;
        if (flag)
            flagPerEvent(event, check);

        trottle.put(check, trottle.getOrDefault(check, 0) + 1);

        int throttle = trottle.getOrDefault(check, 0);

        if (throttle >= 5) {
            for (Player player : Bukkit.getOnlinePlayers())
                message(player, "\2478[\2475E\2478] " + C.RED + violator.getName() + C.GRAY + " has failed " + C.YELLOW + check.getLabel() + C.GRAY + (!message.equals("") ? " [" + C.WHITE + message + C.GRAY + "]" : "") + ".");
            alerts.put(check, alerts.getOrDefault(check, 0) + 1);
            if (alerts.get(check) >= check.getAlertsTillBan()) {
                banPlayer(violator);
                alerts.remove(check);
            }
            trottle.remove(check);
        }
    }

    private void flagPerEvent(Event event, Check check) {
        if (check.isSilent()) return;
        switch (event.getEventName()) {
            case "PlayerMoveEvent":
                ((PlayerMoveEvent) event).setTo(((PlayerMoveEvent) event).getFrom());
                break;
            case "ProjectileLaunchEvent":
                ((ProjectileLaunchEvent) event).setCancelled(true);
                break;
            case "EntityDamageByEntityEvent":
                ((EntityDamageByEntityEvent) event).setCancelled(true);
                break;
            default:
                break;
        }
    }

    private void banPlayer(Player player) {
        String message = "\2478---------------------------------------\n\n\247c\247l" + player.getName() + " has been added to the banwave!\n\n\247r\2478---------------------------------------";
        Bukkit.broadcastMessage(message);
        banwave.add(player);
    }

    public List<Player> getBanwave() {
        return banwave;
    }
}