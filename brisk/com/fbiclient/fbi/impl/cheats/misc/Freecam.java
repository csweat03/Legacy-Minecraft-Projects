package com.fbiclient.fbi.impl.cheats.misc;

import com.mojang.authlib.GameProfile;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Event;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.blocks.BlockCollisionEvent;
import com.fbiclient.fbi.client.events.blocks.PushOutOfBlockEvent;
import com.fbiclient.fbi.client.events.game.PacketEvent;
import com.fbiclient.fbi.client.events.player.MoveEvent;
import com.fbiclient.fbi.client.events.player.UpdateMotionEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Kyle
 * @since 5/6/2018
 **/
@CheatManifest(label = "Freecam", category = Category.MISC, description = "Freely view camera outside body")
public class Freecam extends Cheat {

    private double startX, startY, startZ;
    private float yaw, pitch;

    @Register
    public void update(UpdateMotionEvent event) {
        if (!mc.thePlayer.capabilities.isFlying)
            mc.thePlayer.capabilities.isFlying = true;
        mc.thePlayer.motionY = mc.inGameHasFocus ? mc.gameSettings.keyBindJump.isKeyDown() ? 0.4 : mc.gameSettings.keyBindSneak.isKeyDown() ? -0.4 : mc.thePlayer.motionY : mc.thePlayer.motionY;
    }

    @Register
    public void packet(PacketEvent event) {
        if (event.getType() == Event.Type.OUTGOING) {
            if (event.getPacket() instanceof C03PacketPlayer && !event.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    @Register
    public void motion(MoveEvent event) {
        event.x *= 2.0;
        event.z *= 2.0;
    }

    @Register
    public void pushOutOfBlocks(PushOutOfBlockEvent e) {
        e.setCancelled(true);
    }

    @Register
    public void boundingBox(BlockCollisionEvent e) {
        e.setBoundingBox(null);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.thePlayer != null) {
            mc.renderGlobal.loadRenderers();
            startX = mc.thePlayer.posX;
            startY = mc.thePlayer.posY;
            startZ = mc.thePlayer.posZ;
            yaw = mc.thePlayer.rotationYaw;
            pitch = mc.thePlayer.rotationPitch;
            EntityOtherPlayerMP entity = new EntityOtherPlayerMP(mc.theWorld, new GameProfile(mc.thePlayer.getUniqueID(), mc.thePlayer.getCommandSenderEntity().getName()));
            mc.theWorld.addEntityToWorld(64199, entity);
            entity.setPositionAndRotation(startX, mc.thePlayer.getEntityBoundingBox().minY, startZ, yaw, pitch);
            entity.setSneaking(mc.thePlayer.isSneaking());
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.renderGlobal.loadRenderers();
        mc.thePlayer.setPositionAndRotation(startX, startY, startZ, yaw, pitch);
        mc.thePlayer.noClip = false;
        mc.theWorld.removeEntityFromWorld(64199);
        mc.thePlayer.capabilities.isFlying = false;
    }
}
