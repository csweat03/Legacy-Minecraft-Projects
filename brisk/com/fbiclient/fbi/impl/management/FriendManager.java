package com.fbiclient.fbi.impl.management;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import com.fbiclient.fbi.client.management.Person;
import com.fbiclient.fbi.client.management.types.ArrayListManager;

public class FriendManager extends ArrayListManager<Person> {
	
	private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final String NAME = "https://api.mcuuid.com/json/uuid/%s";
	private final String PROFILE = "https://api.mcuuid.com/json/name/%s";
	private File friendFile;
	private final Map<String, UUID> CACHED_NAMES = new HashMap<String, UUID>();

	public FriendManager(File parentDirectory) {
		super.setup();
		friendFile = new File(parentDirectory + File.separator + "friends.json");
	}

	public void setup() {
		try {
			if (!friendFile.exists()) {
				friendFile.createNewFile();
				this.save();
				return;
			}

			this.load();
		} catch (IOException var2) {
			System.exit(0);
		}

	}

	public List<Person> getFriendList() {
		return this.getRegistry();
	}

	public void add(UUID uuid, String name) {
		this.include(new Person(uuid, name));
		this.save();
	}

	public Optional get(String name) {
		return this.getRegistry().stream().filter((friend) -> {
			return friend.getName().equalsIgnoreCase(name);
		}).findFirst();
	}

	public Optional get(UUID uuid) {
		return this.getRegistry().stream().filter((friend) -> {
			return ((Person) friend).getUUID().equals(uuid);
		}).findFirst();
	}

	public boolean isFriend(UUID uuid) {
		return this.getRegistry().stream().anyMatch((friend) -> {
			return ((Person) friend).getUUID().equals(uuid);
		});
	}

	public void save() {
        try {
            final PrintWriter printWriter = new PrintWriter(friendFile);
            final JsonObject jsonObject = new JsonObject();
            this.getRegistry().forEach(friend -> jsonObject.add(friend.getName(), (JsonElement)new JsonPrimitive(friend.getUUID().toString())));
            printWriter.print(GSON.toJson((JsonElement)jsonObject));
            printWriter.close();
        }
        catch (IOException ex) {}
        catch (NullPointerException ex2) {}
    }
    
    public void load() {
        try {
            final JsonObject jsonObject = new JsonParser().parse((Reader)new FileReader(friendFile)).getAsJsonObject();
            final Set<Map.Entry<String, JsonElement>> elements = (Set<Map.Entry<String, JsonElement>>)jsonObject.entrySet();
            elements.forEach(entry -> this.getRegistry().add(new Person(UUID.fromString(entry.getValue().getAsString()), (String)entry.getKey())));
        }
        catch (IOException ex) {}
    }
    
}
