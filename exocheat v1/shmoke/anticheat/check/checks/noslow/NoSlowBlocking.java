package club.shmoke.anticheat.check.checks.noslow;

import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.interfaces.AlertHelper;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class NoSlowBlocking extends Check {
    private boolean noslowing = false;

    public NoSlowBlocking() {
        super("NoSlow [Blocking]");
    }

    public void onMove() {
        if (noslowing && (isHoldingFood() || isHoldingWeapon()) && onGround()) {
            if (getSpeed(playerMoveEvent) > 0.2) {
                alert.flagPlayer(this,1, AlertHelper.AlertType.BLATANT, playerMoveEvent);
                noslowing = false;
            }
        }
    }

    public void onPlace(){}

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!isActive())
            return;
        if (getPlayer() != event.getPlayer()) return;
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            noslowing = false;
        if (event.getItem() != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
            noslowing = true;
        else
            noslowing = false;
    }
}
