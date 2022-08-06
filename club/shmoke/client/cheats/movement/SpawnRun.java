package club.shmoke.client.cheats.movement;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.api.event.Event;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.util.math.DelayHelper;

public class SpawnRun extends Cheat {

	private DelayHelper helper = new DelayHelper();

	public SpawnRun() {
		super("SpawnRun", Type.MOVEMENT);
	}

	@EventListener
	public void onUpdate(UpdatePlayerEvent e) {
		if (e.type == Event.Type.PRE && mc.thePlayer.fallDistance >= 4F) {
			mc.thePlayer.motionY = 0;
			mc.thePlayer.onGround = true;

			for (int i = 0; i < 3; i++) {
				mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0E-9, mc.thePlayer.posZ);

				if (mc.thePlayer.ticksExisted % 3 == 0)
					mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.0E-12, mc.thePlayer.posZ);
			}
		}
		if (mc.thePlayer.fallDistance <= 3.9) helper.reset();
		if (!helper.hasReached(200) && mc.thePlayer.fallDistance >= 4) {
			mc.thePlayer.setSpeed(1.2);
			mc.timer.timerSpeed = 4F;
		} else if (helper.hasReached(200)) {
			mc.thePlayer.motionY = -0.8D;
			toggle();
		}
	}

	@Override
	public void onEnable() {
		super.onEnable();
		helper.reset();
	}

	@Override
	public void onDisable() {
		super.onDisable();
		mc.thePlayer.setSpeed(0);
		mc.timer.timerSpeed = 1F;
		helper.reset();
	}

}
