package com.fbiclient.fbi.impl.cheats.visual;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.game.PacketEvent;
import com.fbiclient.fbi.client.events.render.RenderGuiEvent;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.world.World;

@CheatManifest(label = "Lights", description = "Adds gamma to the misc", category = Category.VISUAL, visible = false)
public class Lights extends Cheat {

	@Register
	public void handle(final RenderGuiEvent event) {
		addGamma(mc.theWorld);
	}

	private void addGamma(final World world) {
		final float brightness = 1.0f;
		for (int i = 0; i < world.provider.getLightBrightnessTable().length; ++i) {
			world.provider.getLightBrightnessTable()[i] = brightness;
		}
	}

	@Register
	public void handlePackets(PacketEvent event) {
		if (mc.theWorld != null) {
			mc.theWorld.setWorldTime(6000L);
		}
		if (event.getPacket() instanceof S03PacketTimeUpdate) {
			S03PacketTimeUpdate packet = (S03PacketTimeUpdate) event.getPacket();
			packet.setWorldTime(6000L);
		}
	}

	public void onEnable() {
		if (mc.theWorld != null) {
			mc.theWorld.setWorldTime(6000L);
		}
	}

}
