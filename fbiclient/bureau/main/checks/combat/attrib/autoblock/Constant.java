package com.fbiclient.bureau.main.checks.combat.attrib.autoblock;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.fbiclient.bureau.Bureau;
import com.fbiclient.bureau.api.alert.AlertManagment;
import com.fbiclient.bureau.main.checks.combat.Killaura;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    private Map<Player, Integer> blocking = new HashMap<>();

    public Constant() {
        Bureau.getBureau().getProtocolManager().addPacketListener(new PacketAdapter(Bureau.getPlugin(), ListenerPriority.MONITOR, PacketType.Play.Client.BLOCK_PLACE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.BLOCK_PLACE)
                    blocking.put(event.getPlayer(), blocking.getOrDefault(event.getPlayer(), 0) + 1);
                else
                    blocking.remove(event.getPlayer());
            }
        });
        Bureau.getBureau().getProtocolManager().addPacketListener(new PacketAdapter(Bureau.getPlugin(), ListenerPriority.MONITOR, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
                    int block = blocking.getOrDefault(event.getPlayer(), 0) + 1;
                    if (block >= 9) {
                        AlertManagment.flag(Bureau.getBureau().getCheckRegistry().getContent(Killaura.class), event.getPlayer(), "Constant");
                        blocking.remove(event.getPlayer());
                    } else if (block >= 3) {
                        blocking.put(event.getPlayer(), block - 3);
                    }
                }
            }
        });
    }
}
