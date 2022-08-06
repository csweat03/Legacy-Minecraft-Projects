package com.fbiclient.fbi.client.management;

import java.util.UUID;

import com.fbiclient.fbi.client.framework.helper.IHelper;

public class Person implements IHelper {
	private UUID uuid;
	private String name;
	private String playerName;

	public Person(final UUID uuid, final String name) {
		this.uuid = uuid;
		this.name = name;
		new Thread(() -> {
			try {
				this.playerName = PROFILE_HELPER.getName(uuid);
			} catch (Exception ex) {
			}
			if (this.playerName == null) {
				this.playerName = name;
			}
		}).start();
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public String getName() {
		return this.name;
	}

	public String getPlayerName() {
		return this.playerName;
	}
}
