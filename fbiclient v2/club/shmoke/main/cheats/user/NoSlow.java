package club.shmoke.main.cheats.user;

import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import club.shmoke.api.event.EventHandler;
import club.shmoke.main.events.SlowEvent;
import club.shmoke.main.events.UpdateEvent;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Cheat {

    private Property<Mode> mode = new Property<>(this, "Mode", Mode.DEFAULT);

    public NoSlow() {
        super("NoSlow", 0, Category.USER, "Negates slowdown effects");
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        switch (mode.getValue()) {
            case DEFAULT:
                if (mc.thePlayer.isBlocking())
                    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                break;
            case MINEMANCLUB:
                if (!mc.thePlayer.isSwingInProgress && mc.thePlayer.swingProgress <= 0 && mc.thePlayer.isMoving() && mc.thePlayer.onGround && (mc.thePlayer.isBlocking() || mc.thePlayer.isUsingItem()))
                    mc.thePlayer.setSpeed((mc.thePlayer.ticksExisted % 3 == 0) ? (playerUtility.getBaseMoveSpeed() + 0.01) : ((playerUtility.getBaseMoveSpeed() % 2) * 0.9));
                break;
        }
    }

    @EventHandler
    public void onSlowdown(SlowEvent event) {
        if (mode.getValue() == Mode.DEFAULT)
            event.cancel();
    }

    public enum Mode {
        DEFAULT, MINEMANCLUB
    }

}
