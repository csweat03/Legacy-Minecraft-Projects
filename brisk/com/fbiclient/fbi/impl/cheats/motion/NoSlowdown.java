package com.fbiclient.fbi.impl.cheats.motion;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.anticheat.Anticheats;
import com.fbiclient.fbi.client.events.player.SlowdownEvent;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@CheatManifest(label = "No Slowdown", description = "Do not slow down when using items", category = Category.MOTION)
public class NoSlowdown extends Cheat {

	@Register
	public void handleUpdate(UpdateMotionEvent event) {
		boolean isSpecial = Anticheats.findAnticheat() == Anticheats.WATCHDOG || Anticheats.findAnticheat() == Anticheats.AGC;
		switch (event.getType()) {
		case PRE:
			if (mc.thePlayer.isMoving()) {
				if (mc.thePlayer.isUsingItem()) {
					if (Anticheats.findAnticheat() == Anticheats.AGC)
						doMinemanSlowdown();
					if (Anticheats.findAnticheat() == Anticheats.WATCHDOG)
						if (!mc.thePlayer.isBlocking())
							mc.thePlayer.sendQueue
									.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(0, 0, 0), 255,
											mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
				}
				if (mc.thePlayer.isBlocking()) {
					if (!isSpecial)
						mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
								C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
				}
			}
			break;
		case POST:
			if (!isSpecial) {
				if (mc.thePlayer.isBlocking() && mc.thePlayer.isMoving()) {
					mc.getNetHandler()
							.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
				}
			}
			break;
		}
	}

	public void doMinemanSlowdown() {
		mc.thePlayer.setSprinting(false);
		if (mc.thePlayer.onGround) {
			if (!mc.thePlayer.isSwingInProgress && mc.thePlayer.swingProgress <= 0 && !mc.thePlayer.isSprinting()) {
				mc.thePlayer.setSpeed((mc.thePlayer.ticksExisted % 3 == 0) ? (MOTION_HELPER.getBaseMoveSpeed() - 0.02)
						: ((MOTION_HELPER.getBaseMoveSpeed() % 2) * 0.55));
			}
		}
	}

	@Register
	public void handleSlowdown(SlowdownEvent event) {
		boolean isSpecial = Anticheats.findAnticheat() == Anticheats.WATCHDOG || Anticheats.findAnticheat() == Anticheats.AGC;
		if (mc.thePlayer.isUsingItem() && mc.thePlayer.isMoving()) {
			if (isSpecial) {
				if (!mc.thePlayer.isBlocking())
					event.setCancelled(true);
			} else {
				event.setCancelled(true);
			}
		}
	}

}
