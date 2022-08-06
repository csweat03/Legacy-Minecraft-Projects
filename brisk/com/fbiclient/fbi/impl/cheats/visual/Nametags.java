package com.fbiclient.fbi.impl.cheats.visual;

import java.util.Optional;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import me.valkyrie.api.value.Val;
import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Event;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.render.RenderEntityEvent;
import com.fbiclient.fbi.client.events.render.RenderNametagEvent;
import com.fbiclient.fbi.client.management.Person;
import com.fbiclient.fbi.impl.Brisk;
import me.xx.utility.MathUtility;
import me.xx.utility.render.Interpolate;

@CheatManifest(category = Category.VISUAL, description = "Renders enlarged nametags", label = "Nametags")
public class Nametags extends Cheat {

    @Val(label = "Invisible")
    public boolean invis;

    @Val(label = "Armor")
    public boolean armor;

    @Register(priority = Event.Priority.LOW)
    private void onRender(RenderEntityEvent e) {
        GL11.glPushMatrix();
        for (EntityPlayer p : mc.theWorld.playerEntities) {
            if (p == mc.thePlayer || (!p.isEntityAlive() && !Double.isNaN(p.getHealth())) || !RENDER_HELPER.isInFrustumView(p)) {
                continue;
            }
            if (invis && p.isInvisible())
                continue;
            float distance = mc.thePlayer.getDistanceToEntity(p);
            double scale = 0.0027 * (distance - 1 > 1 ? distance : 2);
            GlStateManager.pushMatrix();
            double x = Interpolate.interpolate(p.posX, p.lastTickPosX),
                    y = Interpolate.interpolate(p.posY, p.lastTickPosY),
                    z = Interpolate.interpolate(p.posZ, p.lastTickPosZ);
            x -= mc.getRenderManager().renderPosX;
            y -= mc.getRenderManager().renderPosY;
            z -= mc.getRenderManager().renderPosZ;
            int color = -1;
            if (p.getHealth() >= 20) {
                color = (0x00FF00);
            } else if (p.getHealth() >= 15) {
                color = (0x009900);
            } else if (p.getHealth() >= 8) {
                color = (0x999900);
            } else if (p.getHealth() >= 5) {
                color = (0x990000);
            } else if (p.getHealth() >= 0) {
                color = (0xFF0000);
            }
            Optional<Person> person = Brisk.INSTANCE.getFriendManager().get(p.getUniqueID());
            String name = person.map(f -> f.getName()).orElse(p.getName());
            String health = String.valueOf(MathUtility.round(p.getHealth() / 2, 1)).replace(".0", "");
            String str = String.format("%s%s\2477%s", (p.isSneaking() ? "\2476" : "\247r"), name, Double.isNaN(p.getHealth()) ? "" : ": \247r" + health);
            GlStateManager.translate(x, y + p.height + (p.isSneaking() ? -0.1 : 0.4), z);
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1, 0, 0);
            GlStateManager.scale(-scale, -scale, 0);
            GlStateManager.disableDepth();
            int width = mc.fontRendererObj.getStringWidth(str) / 2;
            GlStateManager.disableTexture2D();
            Gui.drawBorderedRect(-width - 4, -3, (width + 3) + 1, 11, 1, 0x77000000,
                    person.isPresent() ? 0xff89fdff : 0);
            GlStateManager.enableTexture2D();
            mc.fontRendererObj.drawStringWithShadow(str, -width, 0, -1);
            GlStateManager.enableDepth();
            int toRender = 0, itemX;
            for (int i = 0; i < 5; i++) {
                if (p.getEquipmentInSlot(i) == null) {
                    continue;
                }
                toRender++;
            }
            itemX = -(toRender * 9);
            for (int i = 0; i < 5; i++) {
                ItemStack ia = p.getEquipmentInSlot(i);
                if (ia == null) {
                    continue;
                }
                float oldZ = mc.getRenderItem().zLevel;
                GL11.glPushMatrix();
                GlStateManager.clear(GL11.GL_ACCUM);
                GlStateManager.disableAlpha();
                net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
                if (armor) {
                    mc.getRenderItem().zLevel = -100;
                    mc.getRenderItem().renderItemIntoGUI(ia, itemX, (int) -20);
                    mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, ia, itemX, (int) -20);
                    mc.getRenderItem().zLevel = oldZ;
                }
                net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
                GlStateManager.enableAlpha();
                GL11.glPopMatrix();
                itemX += 16;
            }
            GlStateManager.popMatrix();
        }
        GL11.glPopMatrix();
    }

    @Register
    public void onNametags(RenderNametagEvent e) {
        e.setCancelled(true);
    }

}
