package com.fbiclient.bureau.main.checks;

import com.fbiclient.bureau.api.alert.AlertManagment;
import com.fbiclient.bureau.api.check.Check;
import com.fbiclient.bureau.api.check.annotes.CheckManifest;
import com.fbiclient.bureau.api.check.annotes.Experimental;
import com.fbiclient.bureau.api.check.annotes.Prevention;
import com.fbiclient.bureau.main.players.CustomPlayer;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

@Prevention
@Experimental
@CheckManifest(label = "Phase")
public class Phase extends Check {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        CustomPlayer cPlayer = new CustomPlayer(event);

        for (double y = 0.0; y < 0.5; y += 0.1) {
            Block block = event.getFrom().clone().add(0, y, 0).getBlock();

            if ((block.getType().isSolid() && block.getType().isBlock() && cPlayer.getBlockData(block).isSolid())
                    || isQuestioned(cPlayer.getMovement(event).getXDifference(), block)
                    || isQuestioned(cPlayer.getMovement(event).getZDifference(), block))
                AlertManagment.flag(this, event.getPlayer(), WordUtils.capitalizeFully(block.getType().name()));
        }
    }

    private boolean isQuestioned(double dist, Block block) {
        if (dist > 0.7) {
            for (double count = 0.0; count < dist; count += 0.1) {
                Block b = block.getLocation().clone().add(0, 0, count).getBlock();
                if (b.getType().isSolid() && b.getType().isBlock() && b.getType() != Material.AIR)
                    return true;
                count++;
            }
        }
        return false;
    }

}
