package club.shmoke.client.cheats.movement;

import net.minecraft.util.AxisAlignedBB;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.client.events.block.BoundingBoxEvent;
import club.shmoke.client.events.entity.JumpEvent;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.api.event.interfaces.EventListener;

public class Jesus extends Cheat implements IHelper {


	public Jesus() {
		super("Jesus", Type.MOVEMENT);
	}

	@EventListener
	public void onUpdate(UpdatePlayerEvent e) {
		if (BLOCK_HELPER.isOnLiquid() && !BLOCK_HELPER.isInLiquid())
            e.y = (mc.thePlayer.ticksExisted % 2 == 0 ? e.y - 1E-3 : e.y + 1E-3);
	}

	@EventListener
	public void jump(JumpEvent e) {
		if (BLOCK_HELPER.isOnLiquid())
			e.cancel();
	}

	@EventListener
	public void onBB(BoundingBoxEvent e) {
		if (BLOCK_HELPER.isOnLiquid() && mc.thePlayer.boundingBox.minY > e.getBlockPos().getY() + 0.99) {
			e.setBoundingBox(new AxisAlignedBB(e.getBlockPos().getX(), e.getBlockPos().getY(), e.getBlockPos().getZ(),
					e.getBlockPos().getX() + 1, e.getBlockPos().getY() + 1, e.getBlockPos().getZ() + 1));
		}
	}
}