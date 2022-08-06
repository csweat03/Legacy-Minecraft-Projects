package club.shmoke.api.anticheat;

import net.minecraft.client.Minecraft;

public class AnticheatManager
{
    public Anticheat findAnticheat()
    {
        if (!Minecraft.getMinecraft().isSingleplayer())
        {
            for (Anticheat ac : Anticheat.values())
            {
                for (String ip : ac.ips)
                {
                    if (!ip.isEmpty())
                    {
                        if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains(ip))
                        {
                            return ac;
                        }
                    }
                }
            }
        }
        else
        {
            return Anticheat.UNKNOWN;
        }

        return Anticheat.UNKNOWN;
    }
}
