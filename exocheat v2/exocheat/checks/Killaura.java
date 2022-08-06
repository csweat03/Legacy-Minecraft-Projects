package club.shmoke.main.exocheat.checks;

import club.shmoke.main.api.check.Check;
import club.shmoke.main.api.check.CheckInfo;
import club.shmoke.main.exocheat.ExoCheat;
import club.shmoke.main.exocheat.checks.killaura.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author Christian
 */
@CheckInfo(label = "Killaura")
public class Killaura extends Check {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Switch.onCall(event);
        Walls.onCall(event);
        Swing.onCall(event);
        HitMiss.onCall(event);
        Range.onCall(event);
    }

    protected void onMove(Player player, PlayerMoveEvent event) {
        Bot.onCall(event);
    }

    @EventHandler
    public void onSwing(PlayerAnimationEvent event) {
        HitMiss.onCall2(event);
        Swing.onCall2(event);
    }

}
