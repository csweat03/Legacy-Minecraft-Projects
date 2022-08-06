package com.fbiclient.fbi.client.events.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.xx.api.event.Event;
import net.minecraft.network.Packet;
/**
 * @author Kyle
 * @since 2/2/2018
 **/
public class PacketEvent extends Event {

	private Packet packet;
	private List<Packet> packets;

	public PacketEvent(Packet packet, Type type) {
		this.packet = packet;
		this.packets = new ArrayList();
		this.setType(type);
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public void addPostCallPackets(Packet... packets) {
		Arrays.stream(packets).forEach(p -> this.packets.add(p));
	}

	public List<Packet> getPostCallPackets() {
		return packets;
	}

}
