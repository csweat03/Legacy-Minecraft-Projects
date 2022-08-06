package club.shmoke.client.util.manage;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

/**
 * @author Kyle
 * @since 8/30/2017
 **/
public abstract class Module
{
    public String id, label, description;
    public ArrayList<String> aliases;

    public abstract String id();

    public abstract String label();

    public abstract ArrayList<String> aliases();
}
