package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian
 */
public class FakeSwing extends Check {

    private List<Player> swing = new ArrayList<>();

    public FakeSwing() {
        super("Fake Swing [NoSwing]");
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (!isActive() || getPlayer() != event.getPlayer()) return;
        if (!swing.contains(event.getPlayer()))
            alert.flagPlayer(this, 5, AlertHelper.AlertType.POSSIBLE, event);
        swing.remove(event.getPlayer());
    }

    public void onMove() {
    }

    public void onPlace() {
    }

    @EventHandler
    public void onAnimation(PlayerAnimationEvent event) {
        if (event.getAnimationType() == PlayerAnimationType.ARM_SWING) {
            if (!swing.contains(event.getPlayer()))
                swing.add(event.getPlayer());
        }
    }
}
