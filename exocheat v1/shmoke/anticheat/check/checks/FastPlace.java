package club.shmoke.anticheat.check.checks;

import club.shmoke.anticheat.Anticheat;
import club.shmoke.anticheat.check.Check;
import club.shmoke.anticheat.helper.AlertManager;
import club.shmoke.anticheat.helper.TimeHelper;

public class FastPlace extends Check
{
    TimeHelper helper = new TimeHelper();
    
    public FastPlace() {
        super("FastPlace");
    }
    
    public void onPlace() {
        if (blockPlaceEvent.getBlockPlaced() != null && !helper.hasReached(51L)) {
            Anticheat.anticheat.alertManager.alert(blockPlaceEvent.getPlayer(), this, AlertManager.CheckType.PROBABLE, 2);
            if (!isSilent()) {
                blockPlaceEvent.setCancelled(true);
                incrementSlot();
            }
        }
        else
            helper.reset();
    }

    public void onMove() {}
}
