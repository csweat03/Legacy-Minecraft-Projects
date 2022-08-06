package com.fbiclient.fbi.client.anticheat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import org.apache.commons.lang3.text.WordUtils;

/**
 * @author Kyle
 * @since 2/17/2018
 **/
public enum Anticheats {

    WATCHDOG(new String[]{"hypixel.net"}),
    GWEN(new String[]{"mineplex.com"}),
    NCP(new String[]{"hvh.gg", "poke.sexy", "envyclient.com", "unitedgames"}),
    AAC(new String[]{"gommehd.net"}),
    AGC(new String[]{"mineman.club", "minemen.club"}),
    AREA51(new String[]{"faithfulmc", "faithful.net"}),
    GUARDIAN(new String[]{"veltpvp.com", "arcane.cc", "pots.gg", "minehq.com"}),
    SECURITY(new String[]{"security.combo.rip"}),
    ANTIKIDZ(new String[]{"kohi.life"}),
    UNKNOWN(new String[]{"lmao.idk"}),
    SINGLEPLAYER(new String[]{"youhavenofriends.com"});

    String[] ips;

    @Override
    public String toString() {
        return WordUtils.capitalizeFully(this.name());
    }

    Anticheats(String[] ips) {
        this.ips = ips;
    }

    public String[] getIps() {
        return ips;
    }

    public static Anticheats findAnticheat() {
        if (!Minecraft.getMinecraft().isSingleplayer()) {
            for (Anticheats ac : Anticheats.values()) {
                for (String ip : ac.getIps()) {
                    if (!ip.isEmpty()) {
                        ServerData server = Minecraft.getMinecraft().getCurrentServerData();
                        if (server != null && server.serverIP.toLowerCase().contains(ip)) {
                            return ac;
                        }
                    }
                }
            }
        } else {
            return Anticheats.SINGLEPLAYER;
        }
        return Anticheats.UNKNOWN;
    }
}
