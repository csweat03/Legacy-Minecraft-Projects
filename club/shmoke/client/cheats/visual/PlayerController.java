package club.shmoke.client.cheats.visual;

import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.client.events.update.UpdatePlayerEvent;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.util.math.DelayHelper;

public class PlayerController extends Cheat {

	private Property<String> dabSub = new Property(this, "Dab Options");
	public Property<Boolean> left = new Property(this, "Left", true);
	public Property<Boolean> right = new Property(this, "Right", true);
	public Property<Integer> dabDelay = new Property(this, "Delay", 1000, 250, 10000, 50);

	private Property<String> sneakSub = new Property(this, "Sneak Options");
	public Property<Boolean> sneak = new Property(this, "Sneak", true);
	public Property<Integer> sneakDelay = new Property(this, "Delay", 1000, 250, 10000, 50);

	private Property<String> headSub = new Property(this, "Head Options");
	public Property<Boolean> breaker = new Property(this, "Breaker", false);
	public Property<Boolean> stare = new Property(this, "Stare", false);

	private Property<String> hackSub = new Property(this, "Hack Options");
	public Property<Boolean> killaura = new Property(this, "Killaura", false);

	public PlayerController() {
		super("Player Controller", Type.VISUAL);
	}

	DelayHelper sneakTimer = new DelayHelper();

	@EventListener
	public void onUpdate(UpdatePlayerEvent event) {
		for (EntityPlayer e : mc.theWorld.playerEntities) {
			if (e == mc.thePlayer)
				continue;

			if (sneak.getValue()) {
				if (sneakTimer.hasReached(sneakDelay.getValue() / 5))
					e.setSneaking(!e.isSneaking());
				if (sneakTimer.hasReached(sneakDelay.getValue() * 2 / 5))
					sneakTimer.reset();
			}

			if (breaker.getValue()) {
				e.rotationPitch = 180;
			}

			if (stare.getValue()) {
				float[] rots = IHelper.ANGLE_HELPER.getFacePosEntityRemote(mc.thePlayer, e);
				e.rotationYawHead = rots[0] + 180;
				e.rotationPitch = rots[1];
			}
			if (killaura.getValue()) {
				if (mc.thePlayer.getDistanceToEntity(e) <= 6) {
					float[] rots = IHelper.ANGLE_HELPER.getFacePosEntityRemote(mc.thePlayer, e);
					e.swingItem();
					e.rotationYawHead = rots[0] + 180;
					e.rotationPitch = rots[1];
				}
			}
		}
	}
}
