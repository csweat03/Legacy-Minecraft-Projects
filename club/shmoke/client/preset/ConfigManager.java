package club.shmoke.client.preset;

import java.io.IOException;

import club.shmoke.client.preset.presets.Default;
import club.shmoke.client.preset.presets.Hypixel;
import club.shmoke.client.preset.presets.Mineplex;
import club.shmoke.client.util.GameLogger;
import club.shmoke.client.util.manage.ListManager;

public class ConfigManager extends ListManager<Config> {
	private ConfigHandler configHandler;

	public void registerConfigs() throws IOException {
		include(new Default(), new Hypixel(), new Mineplex());
	}

	public void setup() {
		GameLogger.log("Loading Horizon... 83% Complete", false, GameLogger.Type.SYSTEM);

		try {
			registerConfigs();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			for(Config c: getContents()) {
				c.setLabel(c.label);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		configHandler = new ConfigHandler();
		configHandler.setCurrentConfig(new Default());
		GameLogger.log("Loading Horizon... 100% Complete", false, GameLogger.Type.SYSTEM);
	}

	public Config find(String str) {
		for (Config con : getContents()) {
			if (con.label().equalsIgnoreCase(str)) {
				return con;
			}
		}

		return null;
	}

	public ConfigHandler getHandler() {
		return configHandler;
	}
}
