package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Timer extends Check {

    private final HashMap<Player, Integer> packetAmount = new HashMap<>();

    public Timer() {
        super("Timer");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Anticheat.anticheat, () -> {
            for (Object player : Bukkit.getOnlinePlayers()) {
                removePacket((Player) player);
            }
        }, 1L, 1L);
    }

    private void addPacket(Player p) {
        packetAmount.put(p, packetAmount.getOrDefault(p, 1) + 1);
    }

    private void removePacket(Player p) {
            if (packetAmount.getOrDefault(p, 0) <= 1)
                packetAmount.remove(p);
            else
                packetAmount.put(p, packetAmount.get(p) - 1);
    }

    public void onMove() {
        addPacket(getPlayer());
        int amount = packetAmount.getOrDefault(getPlayer(), 1);
        if (amount > 12) {
            alert.flagPlayer(this, 3, AlertHelper.AlertType.POSSIBLE, playerMoveEvent);
            removePacket(getPlayer());
        }
    }

    public void onPlace() {
    }
}
