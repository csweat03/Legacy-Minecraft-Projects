package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class NoFall extends Check {

    private HashMap<Player, Integer> fallDistance = new HashMap<>();

    public NoFall() {
        super("NoFall");
    }

    public void onMove() {
        double difference = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY();
        if (difference >= 0 || isInLiquid(0.2)) {
            fallDistance.remove(getPlayer());
            return;
        }

        if (getLocation().subtract(0, fallDistance.computeIfAbsent(getPlayer(), p -> 0) + 1, 0).getBlock().getType() == Material.AIR)
            fallDistance.put(getPlayer(), fallDistance.get(getPlayer()) + 1);

        if (getLocation().subtract(0, 0.25, 0).getBlock().getType() != Material.AIR && fallDistance.get(getPlayer()) > 3) {
            double newHealth = Math.max(0, getPlayer().getHealth() - (fallDistance.get(getPlayer()) - 5));
            getPlayer().setFallDistance(0);
            getPlayer().setHealth(newHealth);
            fallDistance.remove(getPlayer());
        }
        Anticheat.anticheat.alertManager.debug(this, getPlayer().getName() + " Fall Distance: " + fallDistance.get(getPlayer()) + " | " + onGround());
    }

    public void onPlace() {
    }
}
