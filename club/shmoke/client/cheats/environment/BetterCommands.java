package club.shmoke.client.cheats.environment;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.events.other.SendPacketEvent;
import club.shmoke.api.event.interfaces.EventListener;

public class BetterCommands extends Cheat {

	public BetterCommands() {
		super("Better Commands", Type.ENVIRONMENT);
	}

	@EventListener
	public void onPacket(SendPacketEvent event) {
		if (event.getPacket() instanceof C01PacketChatMessage) {
			C01PacketChatMessage packet = (C01PacketChatMessage) event.getPacket();
			String m = packet.getMessage();
			
			addCommand(m, "gmc", "gamemode 1", event);
			addCommand(m, "gms", "gamemode 0", event);
			addCommand(m, "solonormal", "play solo_normal", event);
			addCommand(m, "soloinsane", "play solo_insane", event);
			addCommand(m, "teamnormal", "play team_normal", event);
			addCommand(m, "teaminsane", "play team_insane", event);
		}
	}

	/**
	 * Adding a trigger for the current input then checks if the parsed string is
	 * contained inside the input string and if does not return null it executes the
	 * output string in a chat message.
	 */
	private void addCommand(String input, String parse, String output, SendPacketEvent event) {
		parse = "/" + parse;
		output = "/" + output;
		if (input.contains(parse)) {
			mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(output));
			event.setCancelled(true);
		}
	}
}
