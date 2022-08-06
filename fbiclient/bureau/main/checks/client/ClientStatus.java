package com.fbiclient.bureau.main.checks.client;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.fbiclient.bureau.Bureau;
import com.fbiclient.bureau.api.check.Check;
import com.fbiclient.bureau.api.check.annotes.CheckManifest;
import com.fbiclient.bureau.main.events.update.UpdateEvent;
import com.fbiclient.utility.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

@CheckManifest(label = "Client-side Anticheat")
public class ClientStatus extends Check {

    private Map<Player, Integer> packetsSent = new HashMap<>();

    public ClientStatus() {
        if (!Bureau.getBureau().hasProtocolLib()) {
            Logger.write("Check [Client-side Anticheat] will not be loaded due to ProtocolLib not being a detected dependency.", Logger.Level.WARNING);
            return;
        }

        Bureau.getBureau().getProtocolManager().addPacketListener(new PacketAdapter(Bureau.getBureau(), ListenerPriority.HIGHEST, PacketType.Play.Client.CUSTOM_PAYLOAD) {
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.CUSTOM_PAYLOAD)
                    packetsSent.put(event.getPlayer(), packetsSent.getOrDefault(event.getPlayer(), 0) + 1);
            }
        });
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            int gangGANG = 751;
            if (packetsSent.getOrDefault(player, 0) == gangGANG) {
                if (!player.getDisplayName().contains("✓"))
                    player.setDisplayName("\2478\247l[\247a\247l✓\2478\247l] \247r" + player.getName());
                packetsSent.remove(player);
            }
        }
    }

}
