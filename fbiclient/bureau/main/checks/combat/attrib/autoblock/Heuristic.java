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

public class Heuristic {

    /* Note this is between the player blocking and them attacking*/
    private Map<Player, Long> block = new HashMap<>(), attack = new HashMap<>(), totals = new HashMap<>();

    private Map<Player, Integer> leniency = new HashMap<>();

    public Heuristic() {
        Bureau.getBureau().getProtocolManager().addPacketListener(new PacketAdapter(Bureau.getPlugin(), ListenerPriority.MONITOR, PacketType.Play.Client.BLOCK_PLACE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.BLOCK_PLACE) {
                    block.remove(event.getPlayer());
                    block.put(event.getPlayer(), System.currentTimeMillis());
                }
            }
        });
        Bureau.getBureau().getProtocolManager().addPacketListener(new PacketAdapter(Bureau.getPlugin(), ListenerPriority.MONITOR, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
                    attack.put(event.getPlayer(), System.currentTimeMillis());

                    long difference = attack.getOrDefault(event.getPlayer(), -1L) - block.getOrDefault(event.getPlayer(), -1L);

                    if (difference < 200) {
                        if (totals.getOrDefault(event.getPlayer(), -1L) >= 0 && totals.getOrDefault(event.getPlayer(), -1L) >= difference - 5 && totals.getOrDefault(event.getPlayer(), -1L) <= 5L) {
                            leniency.put(event.getPlayer(), leniency.getOrDefault(event.getPlayer(), 0) + 1);
                        } else if (leniency.getOrDefault(event.getPlayer(), 0) >= 1) {
                            leniency.put(event.getPlayer(), leniency.getOrDefault(event.getPlayer(), 0) - 1);
                        }
                        totals.put(event.getPlayer(), difference);
                        block.remove(event.getPlayer());
                    }

                    if (leniency.getOrDefault(event.getPlayer(), 0) >= 5) {
                        AlertManagment.flag(Bureau.getBureau().getCheckRegistry().getContent(Killaura.class), event.getPlayer(), "Heuristic");
                        leniency.remove(event.getPlayer());
                    }

                }
            }
        });
    }

}
