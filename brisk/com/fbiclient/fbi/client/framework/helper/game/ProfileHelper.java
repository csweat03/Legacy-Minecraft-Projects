package com.fbiclient.fbi.client.framework.helper.game;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ProfileHelper {
	
	private static Map<String, UUID> CACHED_NAMES = new HashMap<String, UUID>();
	private static String NAME = "https://api.mojang.com/users/profiles/minecraft/%s";
	private static String PROFILE = "https://sessionserver.mojang.com/session/minecraft/profile/%s";

	public UUID getUUID(String name) {
		if (ProfileHelper.CACHED_NAMES.containsKey(name)) {
			return ProfileHelper.CACHED_NAMES.get(name);
		}
		try {
			Reader uuidReader = new InputStreamReader(
					new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s", name)).openStream());
			JsonObject jsonObject = new JsonParser().parse(uuidReader).getAsJsonObject();
			String unfomatted = jsonObject.get("id").getAsString();
			String formatted = "";
			for (int length : new int[] { 8, 4, 4, 4, 12 }) {
				formatted += "-";
				for (int i = 0; i < length; ++i) {
					formatted += unfomatted.charAt(0);
					unfomatted = unfomatted.substring(1);
				}
			}
			formatted = formatted.substring(1);
			UUID uuid = UUID.fromString(formatted);
			ProfileHelper.CACHED_NAMES.put(name, uuid);
			return uuid;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public String getName(UUID uuid) {
		try {
			if (ProfileHelper.CACHED_NAMES.containsValue(uuid)) {
				return ProfileHelper.CACHED_NAMES.entrySet().stream().filter(entry -> uuid == entry.getValue())
						.findFirst().get().getKey();
			}
		} catch (Exception ex) {
		}
		try {
			Reader uuidReader = new InputStreamReader(
					new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s",
							uuid.toString().replaceAll("-", ""))).openStream());
			JsonObject jsonObject = new JsonParser().parse(uuidReader).getAsJsonObject();
			String name = jsonObject.get("name").getAsString();
			ProfileHelper.CACHED_NAMES.put(name, uuid);
			return name;
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}

}
