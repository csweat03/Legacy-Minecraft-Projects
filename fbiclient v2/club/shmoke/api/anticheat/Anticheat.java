package club.shmoke.api.anticheat;

import net.minecraft.client.Minecraft;

public enum Anticheat {

    WATCHDOG(new String[]{"hypixel.net"}),
    MINEPLEX(new String[]{"mineplex.com"}),
    VANILLA(new String[]{"too", "lazy"});

    private String[] ips;

    Anticheat(String[] ips) {
        this.ips = ips;
    }

    public String[] getIPs() {
        return ips;
    }

    public static Anticheat findAnticheat() {
        for (Anticheat anticheat : Anticheat.values())
            for (String s : anticheat.getIPs())
                if (Minecraft.getMinecraft().getCurrentServerData() != null && Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains(s))
                    return anticheat;
        return Anticheat.VANILLA;
    }
}
