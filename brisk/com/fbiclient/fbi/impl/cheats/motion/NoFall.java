package com.fbiclient.fbi.impl.cheats.motion;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import me.valkyrie.api.value.Val;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Event;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.game.PacketEvent;
import com.fbiclient.fbi.client.events.game.TickEvent;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;

@CheatManifest(label = "No Fall", description = "Take no fall damage", category = Category.MOTION)
public class NoFall extends Cheat {

	@Val(label = "Mode")
	public Mode mode;

	public NoFall() {
		mode = Mode.PACKET;
	}

	@Register
	public void handleTicking(TickEvent e) {
		setSuffix(mode.name());
	}

	@Register
	public void packetSend(PacketEvent event) {
		if (event.getType() == Event.Type.OUTGOING) {
			switch (mode) {
			case PACKET:
				if (event.getPacket() instanceof C03PacketPlayer) {
					if (!Minecraft.getMinecraft().thePlayer.onGround) {
						C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
						packet.setOnGround(true);
					}
				}
				break;
			}
		}
	}

	@Register
	public void handleUpdateing(UpdateMotionEvent event) {
		switch (event.getType()) {
		case PRE:
			switch (mode) {
			case GROUND:
				if (mc.thePlayer.fallDistance > 3 && !mc.thePlayer.onGround) {
					if (ENTITY_HELPER.getDistanceToFall(mc.thePlayer) < 20) {
						mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
						mc.thePlayer.isInWater();
						event.setOnGround(true);
					}
				}
				break;
			}
			break;
		}
	}

	public enum Mode {
		PACKET, GROUND, TEST
	}
}
