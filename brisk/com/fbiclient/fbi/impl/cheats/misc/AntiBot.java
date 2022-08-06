package com.fbiclient.fbi.impl.cheats.misc;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Event;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.anticheat.Anticheats;
import com.fbiclient.fbi.client.events.game.PacketEvent;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.util.MathHelper;

/**
 * @author Kyle
 * @since 5/7/2018
 **/
@CheatManifest(label = "Anti Bot", category = Category.MISC, description = "Removes entities detected as bots")
public class AntiBot extends Cheat {

    void suffix() {
        boolean yote = Anticheats.findAnticheat() == Anticheats.WATCHDOG || Anticheats.findAnticheat() == Anticheats.GWEN;
        String antiyeet = String.format("%s", yote ? Anticheats.findAnticheat().toString() : "ID");
        String suff = String.format("%s", antiyeet);
        setSuffix(suff);
    }

    @Register
    public void handleUpdates(UpdateMotionEvent event) {
        suffix();
        for (Entity entity : mc.theWorld.playerEntities) {
            if (entity instanceof EntityPlayer) {
                NetworkPlayerInfo networkPlayerInfo = mc.getNetHandler()
                        .getPlayerInfo(entity.getUniqueID());
            /*if(networkPlayerInfo.getResponseTime() == 0 && mc.thePlayer.getDistanceToEntity(entityPlayer) < 14) {
                if(entityPlayer != mc.thePlayer)
					mc.theWorld.removeEntity(entityPlayer);
			}*/

			/*if(!entityPlayer.hasCustomName()) {
                if(mc.thePlayer != entityPlayer)
					mc.theWorld.removeEntity(entityPlayer);
			}*/

                switch (Anticheats.findAnticheat()) {
                    case UNKNOWN: {
                        if (entity != null && entity != mc.thePlayer) {
                            try {
                                if (networkPlayerInfo.getGameType().isSurvivalOrAdventure() || networkPlayerInfo.getGameType().isCreative()) {
                                    break;
                                }
                            } catch (Exception e) {
                                mc.theWorld.removeEntity(entity);
                            }
                        }
                        break;
                    }
                    case WATCHDOG:
                        if (entity != null && entity != mc.thePlayer) {
                            if (entity.getDisplayName().getFormattedText()
                                    .equalsIgnoreCase(entity.getName() + "\247r")
                                    && !mc.thePlayer.getDisplayName().getFormattedText()
                                    .equalsIgnoreCase(mc.thePlayer.getName() + "\247r")) {
                                mc.theWorld.removeEntity(entity);
                            }
                        }
                        break;
                    case GWEN:
                        if (entity != null && entity != mc.thePlayer) {
                            if (entity.getName().startsWith("Body #") || (((EntityPlayer) entity).getHealth() != 20 && !Double.isNaN(((EntityPlayer) entity).getHealth()))) {
                                mc.theWorld.removeEntity(entity);
                            }
                        }
                        break;
                }
            }
        }
    }

    @Register
    public void handlePackets(PacketEvent event) {
        switch (Anticheats.findAnticheat()) {
            case GWEN: {
                if (event.getType() == Event.Type.INCOMING && event.getPacket() instanceof S0CPacketSpawnPlayer) {
                    S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) event.getPacket();
                    if (mc.thePlayer != mc.theWorld.getEntityByID(packet.getEntityID())) {
                        double entX = (packet.getX() / 32),
                                entY = (packet.getY() / 32),
                                entZ = (packet.getZ() / 32);

                        double distX = mc.thePlayer.posX - entX,
                                distY = mc.thePlayer.posY - entY,
                                distZ = mc.thePlayer.posZ - entZ;

                        float distance = MathHelper.sqrt_double(distX * distX + distY * distY + distZ * distZ);
                        if (distance <= 17.0F && entY > mc.thePlayer.posY + 1.0D && mc.thePlayer.posX != entX && mc.thePlayer.posY != entY && mc.thePlayer.posZ != entZ) {
                            event.setCancelled(true);
                        }
                    }
                }
                break;
            }
            default: {
                break;
            }
        }
    }

}
