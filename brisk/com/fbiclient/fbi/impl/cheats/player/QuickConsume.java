package com.fbiclient.fbi.impl.cheats.player;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Event;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;

@CheatManifest(label = "Quick Consume", description = "Consume edible items faster", category = Category.PLAYER)
public class QuickConsume extends Cheat {

	@Register
	public void handleMotion(UpdateMotionEvent e) {
		if (e.getType() == Event.Type.POST)
			return;
		if (mc.thePlayer.getItemInUseDuration() > 16 && !(mc.thePlayer.getItemInUse().getItem() instanceof ItemSword) && !(mc.thePlayer.getItemInUse().getItem() instanceof ItemBow)) {
			mc.timer.timerSpeed = 1.3F;
		} else if (mc.timer.timerSpeed == 1.3F) {
			mc.timer.timerSpeed = 1.0F;
		}
	}
}
