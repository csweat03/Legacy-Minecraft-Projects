package club.shmoke.api.anticheat;

public enum Anticheat
{
    WATCHDOG(new String[] { "hypixel.net" }),
    GWEN(new String[] { "mineplex" }),
    NCP(new String[] { "envyclient", "arcadian", "hvh.gg" }),
    AAC(new String[] { "" }),
    CUBECRAFT(new String[] { "cubecraft" }),
    AREA51(new String[] { "faithfulmc" }),
    GUARDIAN(new String[] { "veltpvp", "skylandkits", "arcane.cc", "pots.gg", "minehq"}),
    JANITOR(new String[] { "" }),
    SPARTAN(new String[] { "" }),
    MMC(new String[] { "mineman.club" }),
    UNKNOWN(new String[] { "LOLOLOLOLOLOLOLOLOLOLOLO.LOL", "youhavenofriends.com" }),
    ANTIKIDZ(new String[] {"kohi.life"});

    public String[] ips;

    Anticheat(String[] ips)
    {
        this.ips = ips;
    }
}
