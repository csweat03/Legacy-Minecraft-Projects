package club.shmoke.client.preset;

import java.util.ArrayList;

import club.shmoke.client.Client;
import club.shmoke.api.anticheat.Anticheat;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.util.manage.ListManager;
import club.shmoke.client.util.manage.Module;

/**
 * @author Kyle
 * @since Jan 1, 2018
 */
public abstract class Config extends Module
{
    private Anticheat anticheat;

    private BlacklistedManager blackManager;

    public Config(String label, Enum AntiCheat)
    {
    	this.label = id = label;
        blackManager = new BlacklistedManager();
        addBlacklists();
    }

    public abstract void setValues();
    public abstract void setEnabled();

    public abstract void addBlacklists();

    public String label()
    {
        return label;
    }

    public void setLabel(String str)
    {
        label = str;
    }

    public String id()
    {
        return id;
    }

    public void setId(String str)
    {
        id = str;
    }

    public ArrayList<String> aliases()
    {
        return aliases;
    }

    public boolean isCurrent()
    {
        return (Client.INSTANCE.getConfigManager().getHandler().getCurrentConfig() == this);
    }
    
    public void setCurrent()
    {
        Client.INSTANCE.getConfigManager().getHandler().setCurrentConfig(this);
    }

    public Anticheat getAnticheat()
    {
        return anticheat;
    }

    public void addBlacklist(Class cheat)
    {
        blackManager.add(Client.INSTANCE.getCheatManager().get(cheat));
    }

    public BlacklistedManager getBlacklists()
    {
        return blackManager;
    }

    public class BlacklistedManager extends ListManager<Cheat>
    {
        public void setup()
        {
        }
    }
}
