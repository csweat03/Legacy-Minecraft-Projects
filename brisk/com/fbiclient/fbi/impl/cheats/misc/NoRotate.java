package com.fbiclient.fbi.impl.cheats.misc;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.game.PacketEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@CheatManifest(label = "No Rotate", description = "Prevents server from setting your rotations", category = Category.MISC, visible = false)
public class NoRotate extends Cheat {

	@Register
	public void handlePacketData(PacketEvent event) {
		if (mc.theWorld != null) {
			if (event.getPacket() instanceof S08PacketPlayerPosLook) {
				S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
				packet.setYaw(Minecraft.getMinecraft().thePlayer.rotationYaw);
				packet.setPitch(Minecraft.getMinecraft().thePlayer.rotationPitch);
			}
		}
	}
}
