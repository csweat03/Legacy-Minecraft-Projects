package club.shmoke.client.cheats.visual;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.api.cheat.helpers.interfaces.IHelper;
import club.shmoke.api.event.Event;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.Client;
import club.shmoke.client.events.render.RenderESPEvent;
import club.shmoke.client.events.render.RenderEntityEvent;
import club.shmoke.client.events.render.RenderNametagEvent;
import club.shmoke.client.events.render.RenderWorldEvent;
import club.shmoke.client.util.render.GLUtils;
import club.shmoke.client.util.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class ESP extends Cheat {

    public Property<Boolean> playerESP = new Property(this, "Entities", true);
    private final Property<Mode> playerMode = new Property(this, "Mode", Mode.DEFAULT);
    private final Property<Boolean> chest = new Property(this, "Chests", false);
    private Property<String> entities = new Property<>(this, "Entities");
    private final Property<Boolean> thruBlocks = new Property(this, "Through Blocks", false);
    private final Property<Boolean> highlight = new Property(this, "Highlight", false);
    private final Property<Boolean> player = new Property(this, "Players", true);
    private final Property<Boolean> passive = new Property(this, "Passive", false);
    private final Property<Boolean> hostile = new Property(this, "Hostile", false);

    public ESP() {
        super("ESP", Type.VISUAL);
    }

    @EventListener
    public void onRender(RenderESPEvent event) {
        esp2d();
        blocks();
    }

    @EventListener
    public void onRender(RenderWorldEvent event) {
        if (highlight.getValue())
            highlight();
    }

    @EventListener
    public void onRenderNametag(RenderNametagEvent event) {
        if ((event.getEntity() instanceof EntityOtherPlayerMP) && highlight.getValue())
            event.cancel();
    }

    @EventListener
    public void render(RenderEntityEvent e) {
        if (!thruBlocks.getValue()) return;
        if (e.getType() == Event.Type.PRE) {
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -2000000F);
        } else if (e.getType() == Event.Type.POST) {
            GL11.glPolygonOffset(1.0F, 2000000F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }

    private void highlight() {
        for (Object i : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if ((i instanceof EntityPlayer)) {
                EntityPlayer entity = (EntityPlayer) i;
                if (entity.isInvisible() || entity.getName().contains("-")) continue;

                if ((!entity.getName().equals(mc.thePlayer.getName()))) {
                    EntityOtherPlayerMP ep = (EntityOtherPlayerMP) entity;
                    double n = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * mc.timer.renderPartialTicks;
                    mc.getRenderManager();
                    double pX = n - RenderManager.renderPosX;
                    double n2 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * mc.timer.renderPartialTicks;
                    mc.getRenderManager();
                    double pY = n2 - RenderManager.renderPosY;
                    double n3 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * mc.timer.renderPartialTicks;
                    mc.getRenderManager();
                    double pZ = n3 - RenderManager.renderPosZ;
                    GLUtils.preState();
                    renderNameTag(ep, ep.getName(), pX, pY, pZ);
                    GLUtils.postState();
                }
            }
        }
    }

    private void renderArmor(EntityPlayer player, int x, int y) {
        ItemStack[] items = player.getInventory();
        ItemStack inHand = player.getCurrentEquippedItem();
        ItemStack boots = items[0];
        ItemStack leggings = items[1];
        ItemStack body = items[2];
        ItemStack helm = items[3];
        ItemStack[] stuff;

        if (inHand != null) {
            stuff = new ItemStack[]{inHand, helm, body, leggings, boots};
        } else {
            stuff = new ItemStack[]{helm, body, leggings, boots};
        }

        List<ItemStack> stacks = new ArrayList();
        ItemStack[] array;
        int length = (array = stuff).length;
        ItemStack i;

        for (int j = 0; j < length; j++) {
            i = array[j];

            if ((i != null) && (i.getItem() != null)) {
                stacks.add(i);
            }
        }

        int width = 16 * stacks.size() / 2;
        x -= width;
        GlStateManager.disableDepth();

        for (ItemStack stack : stacks) {
            renderItem(stack, x, y);
            x += 16;
        }

        GlStateManager.enableDepth();
    }

    static class EnchantEntry {
        private final Enchantment enchant;
        private final String name;

        EnchantEntry(Enchantment enchant, String name) {
            this.enchant = enchant;
            this.name = name;
        }

        Enchantment getEnchant() {
            return this.enchant;
        }

        String getName() {
            return this.name;
        }
    }

    private void renderItem(ItemStack stack, int x, int y) {
        EnchantEntry[] enchants = {new EnchantEntry(Enchantment.field_180310_c, "Prot"),
                new EnchantEntry(Enchantment.thorns, "Th"), new EnchantEntry(Enchantment.field_180314_l, "Sharp"),
                new EnchantEntry(Enchantment.fireAspect, "Fire"), new EnchantEntry(Enchantment.field_180313_o, "Kb"),
                new EnchantEntry(Enchantment.unbreaking, "Unb")
        };
        GlStateManager.pushMatrix();
        GlStateManager.pushMatrix();
        float scale = 0.3f;
        GlStateManager.translate(x - 3, y + 10, 0.0f);
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.popMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().zLevel = -100.0f;
        GlStateManager.disableDepth();
        mc.getRenderItem().renderItemAboveHead(stack, x, y);
        mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, stack, x, y, null);
        GlStateManager.enableDepth();
        EnchantEntry[] array;

        for (int length = (array = enchants).length, i = 0; i < length; ++i) {
            EnchantEntry enchant = array[i];
            int level = EnchantmentHelper.getEnchantmentLevel(enchant.getEnchant().effectId, stack);
            String levelDisplay = String.valueOf(level);

            if (level > 10) {
                levelDisplay = "10+";
            }

            if (level > 0) {
                float scale1 = 0.32f;
                GlStateManager.translate(x + 2, y + 1, 0.0f);
                GlStateManager.scale(scale1, scale1, scale1);
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GL11.glColor4d(Color.white.getRed() / 255.0f, Color.white.getGreen() / 255.0f, Color.white.getBlue() / 255.0f, Color.white.getAlpha() / 255.0f);
                mc.fontRendererObj.drawString("\247f" + enchant.getName() + " " + levelDisplay,
                        20 - mc.fontRendererObj
                                .getStringWidth("\247f" + enchant.getName() + " " + levelDisplay) / 2,
                        0, Color.WHITE.getRGB());
                GL11.glColor4d(Color.white.getRed() / 255.0f, Color.white.getGreen() / 255.0f, Color.white.getBlue() / 255.0f, Color.white.getAlpha() / 255.0f);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                GlStateManager.scale(3.125f, 3.125f, 3.125f);
                GlStateManager.translate(-x - 1, -y - 1, 0.0f);
                y += (int) ((mc.fontRendererObj.FONT_HEIGHT + 3) * 0.32f);
            }
        }

        mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }

    private void renderNameTag(EntityPlayer entity, String tag, double pX, double pY, double pZ) {
        FontRenderer var12 = mc.fontRendererObj;
        boolean healthBool = true;

        if (entity.getName().equals(mc.thePlayer.getName())) {
            return;
        }

        pY += (entity.isSneaking() ? 0.5D : 0.7D);
        float var13 = mc.thePlayer.getDistanceToEntity(entity) / 4.0F;

        if (var13 < 1.6F) {
            var13 = 1.6F;
        }

        int color = 0xFFFFFF;

        if (entity.isSneaking()) {
            color = 0xFFFC0000;
        } else if (entity.isInvisible()) {
            color = 0x808080;
        }

        Color friendColor = new Color(60, 60, 60);

        if (Client.INSTANCE.getFriendManager().has(entity.getName())) {
            if (entity.getName() != null) {
                tag = "\2473" + entity.getName();
                friendColor = new Color(66, 147, 179);
            }
        }

        int health = (int) entity.getHealth();
        Color colorr = new Color(255,255,255);

        if (health <= entity.getMaxHealth() * 0.25D)
            colorr = new Color(255,0,0);
        else if (health <= entity.getMaxHealth() * 0.5D)
            colorr = new Color(255,255 / 2, 0);
        else if (health <= entity.getMaxHealth() * 0.75D)
            colorr = new Color(255,255, 0);
        else
            colorr = new Color(0,255,0);

        RenderManager renderManager = mc.getRenderManager();
        float scale = var13;
        scale /= 100.0F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) pX, (float) pY + 1.4F, (float) pZ);
        GL11.glNormal3f(1.0F, 1.0F, 1.0F);
        GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-scale, -scale, scale);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        Tessellator var14 = Tessellator.getInstance();
        WorldRenderer var15 = var14.getWorldRenderer();
        int width = mc.fontRendererObj.getStringWidth(tag) / 2;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Gui.drawRect(-width - 2, -11, width + 2 - ((entity.getMaxHealth() - health) * (2)), -10, colorr.getRGB());
        Gui.drawRect(-width - 2, -(mc.fontRendererObj.FONT_HEIGHT + 1), width + 2, 2.0F,
                new Color(20, 20, 20, 160).getRGB());
        Gui.drawBorderedRect(-width - 2, -(mc.fontRendererObj.FONT_HEIGHT + 1), width + 2, 2.0F,
                1.2f, friendColor.getRGB(), 0);
        GLUtils.preState();
        Client.INSTANCE.getFontManager().c18.drawCenteredString(tag, -width + (width),
                -(Client.INSTANCE.getFontManager().c18.getHeight()), color);
        GLUtils.postState();

        GlStateManager.translate(0.0F, 1.0F, 0.0F);
        renderArmor(entity, 0, -(mc.fontRendererObj.FONT_HEIGHT + 1) - 20);
        GlStateManager.translate(0.0F, -1.0F, 0.0F);

        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    private boolean isValid(Entity e) {
        if (passive.getValue() && (e instanceof EntityAnimal || e instanceof EntityVillager))
            return true;

        if (player.getValue() && e instanceof EntityPlayer)
            return true;

        if (hostile.getValue() && (e instanceof EntityMob || e instanceof EntitySlime || e instanceof EntityDragon))
            return true;

        return false;
    }

    private void esp2d() {
        for (Entity o : mc.theWorld.loadedEntityList) {
            if (ENTITY_HELPER.isValidEntityType(o, player.getValue(), passive.getValue(), hostile.getValue())) {
                Entity ent = (Entity) o;
                if (!(ent instanceof EntityPlayerSP) && !ent.isInvisible()
                        && mc.thePlayer.getDistanceToEntity(ent) <= 80F) {
                    if (!IHelper.ANGLE_HELPER.isWithinFOV(ent, 90) || mc.gameSettings.thirdPersonView > 1)
                        continue;
                    GlStateManager.pushMatrix();
                    GlStateManager
                            .translate(
                                    (float) ent.lastTickPosX
                                            + (ent.posX - ent.lastTickPosX)
                                            * Minecraft.getMinecraft().timer.renderPartialTicks
                                            - RenderManager.renderPosX,
                                    ent.lastTickPosY
                                            + (ent.posY - ent.lastTickPosY)
                                            * Minecraft.getMinecraft().timer.renderPartialTicks
                                            - RenderManager.renderPosY,
                                    ent.lastTickPosZ
                                            + (ent.posZ - ent.lastTickPosZ)
                                            * Minecraft.getMinecraft().timer.renderPartialTicks
                                            - RenderManager.renderPosZ);
                    GlStateManager.rotate(-Minecraft.getMinecraft().renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                    GlStateManager.scale(-0.018F, -0.018F, 0.018F);
                    GlStateManager.disableDepth();

                    if (o instanceof EntityPlayer) {
                        EntityPlayer en = (EntityPlayer) o;
                        if (playerMode.getValue() == Mode.BOX) {
                            Color xd = Client.INSTANCE.color;
                            int coloro = new Color(xd.getRed(), xd.getGreen(), xd.getBlue(), 100).getRGB();
                            esp(en.hurtTime > 0 ? new Color(255, 0, 0, 100).getRGB() : coloro);
                        } else {
                            esp(en.hurtTime > 0 && mc.thePlayer.getDistanceToEntity(en) <= 6
                                    ? new Color(255, 0, 0).getRGB()
                                    : Client.INSTANCE.color.getRGB());
                        }
                    } else {
                        if (playerMode.getValue() == Mode.BOX) {
                            Color xd = Client.INSTANCE.color;
                            int coloro = new Color(xd.getRed(), xd.getGreen(), xd.getBlue(), 100).getRGB();
                            esp(coloro);
                        } else {
                            esp(Client.INSTANCE.color.getRGB());
                        }
                    }

                    GlStateManager.enableDepth();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.popMatrix();
                }
            }
        }
    }

    private void esp(int color) {
        if (playerMode.getValue() == Mode.DEFAULT) {
            Gui.drawRect(-27, -111, -22, 3, 0xef000000);
            Gui.drawRect(22, -111, 27, 3, 0xef000000);
            Gui.drawRect(-27, -112, 27, -107, 0xef000000);
            Gui.drawRect(-27, 2, 27, 7, 0xef000000);

            Gui.drawRect(-26, -111, -23, 3, color);
            Gui.drawRect(23, -111, 26, 3, color);
            Gui.drawRect(-26, -111, 26, -108, color);
            Gui.drawRect(-26, 3, 26, 6, color);
        }
        if (playerMode.getValue() == Mode.CORNERS) {
            Gui.drawRect(-27, -111, -22, -91, 0xef000000);
            Gui.drawRect(-22, -111, -6, -106, 0xef000000);
            Gui.drawRect(-26.2, -110.2, -6.8, -106.8, color);
            Gui.drawRect(-26.2, -106.8, -22.8, -91.8, color);

            Gui.drawRect(27, -111, 22, -91, 0xef000000);
            Gui.drawRect(22, -111, 6, -106, 0xef000000);
            Gui.drawRect(6.8, -110.2, 26.2, -106.8, color);
            Gui.drawRect(22.8, -106.8, 26.2, -91.8, color);
            /////////////////////////////////////////////////
            Gui.drawRect(-27, 3, -22, -20, 0xef000000);
            Gui.drawRect(-22, 3, -6, -3, 0xef000000);
            Gui.drawRect(-26.2, -19.8, -22.8, 2.2, color);
            Gui.drawRect(-22.8, -2.2, -6.8, 2.2, color);

            Gui.drawRect(27, 3, 22, -20, 0xef000000);
            Gui.drawRect(22, 3, 6, -3, 0xef000000);
            Gui.drawRect(22.8, -19.8, 26.2, 2.2, color);
            Gui.drawRect(6.8, -2.2, 22.8, 2.2, color);
        }
        if (playerMode.getValue() == Mode.BOX) {
            Gui.drawRect(-35, -111, 35, 8, color);
        }
    }

    private void blocks() {
        for (Object o : mc.theWorld.loadedTileEntityList) {
            if (chest.getValue() && (o instanceof TileEntityChest))
                RenderUtils.drawBlockESP(((TileEntityChest) o).getPos(), Client.INSTANCE.color.getRed() / 255.0, Client.INSTANCE.color.getGreen() / 255.0, Client.INSTANCE.color.getBlue() / 255.0, 0.20000000298023224);
            if (chest.getValue() && (o instanceof TileEntityEnderChest))
                RenderUtils.drawBlockESP(((TileEntityEnderChest) o).getPos(), Client.INSTANCE.color.getRed() / 255.0, Client.INSTANCE.color.getGreen() / 255.0, Client.INSTANCE.color.getBlue() / 255.0, 0.20000000298023224);
        }
    }

    public enum Mode {
        DEFAULT, CORNERS, BOX
    }

}
