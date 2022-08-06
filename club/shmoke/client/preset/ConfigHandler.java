package club.shmoke.client.preset;

import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;

public class ConfigHandler<T extends Config>
{
    private T currentConfig;

    public T getCurrentConfig()
    {
        return currentConfig;
    }

    public void setCurrentConfig(T currentConfig)
    {
        this.currentConfig = currentConfig;

        for (Cheat m : Client.INSTANCE.getCheatManager().getContents())
        {
        	if(m.isBlacklisted()) {
        		if(m.getState()) {
        			m.toggle();
        		}
        	}
        }
        this.currentConfig.setEnabled();
        this.currentConfig.setValues();
    }
}