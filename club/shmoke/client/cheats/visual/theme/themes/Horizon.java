package club.shmoke.client.cheats.visual.theme.themes;

import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.theme.Theme;
import club.shmoke.client.Client;
import club.shmoke.client.cheats.visual.theme.ThemeHelper;
import club.shmoke.client.ui.overlay.tab.HorizonTab;
import club.shmoke.client.util.math.DelayHelper;
import club.shmoke.client.util.render.GLUtils;
import club.shmoke.client.util.render.RenderUtils;
import club.shmoke.client.util.render.font.FontManager;
import club.shmoke.client.util.render.font.FontManager.CFontRenderer;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class Horizon extends Theme implements ThemeHelper {

    public int x, y, scale;
    double yAnim = 0;
    Color col = new Color(Client.INSTANCE.color.getRGB());
    private DelayHelper time = new DelayHelper();

    @Override
    public void render() {
        // watermark
        if (o.watermark.getValue())
            watermark();
        // arraylist
        if (o.arraylist.getValue())
            arraylist();
        // information
        if (o.info.getValue())
            information();
        // armor
        if (o.armor.getValue())
            armor();
        // potion
        if (o.potions.getValue())
            potions();
        // notifications
        if (o.notifications.getValue())
            Client.INSTANCE.getNotificationManager().renderNotifications();
    }

    public void renderTab() {
        Client.INSTANCE.getTab().onRender(0, o.watermark.getValue() ? 13 : 0, 10, 13, Client.INSTANCE.color.getRGB(), Client.INSTANCE.color.brighter().getRGB());
    }

    private void watermark() {
        if (HorizonTab.delay.hasReached(2500) && col.getAlpha() > 60)
            col = new Color(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha() - 1);
        else if (HorizonTab.delay.hasReached(2500)) col = new Color(col.getRed(), col.getGreen(), col.getBlue(), 60);
        else if (col.getAlpha() < 255) col = new Color(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha() + 1);
        GLUtils.preState();
        Client.INSTANCE.getFontManager().c12.drawStringWithShadow("b" + Client.INSTANCE.build(),
                Client.INSTANCE.getFontManager().c24.getStringWidth(Client.INSTANCE.label()) + 1, 3.0f, new Color(155,155,155,col.getAlpha()).getRGB());
        Client.INSTANCE.getFontManager().c24.drawStringWithShadow(Client.INSTANCE.label(), 1.0f,
                (mc.isFullScreen() ? 1.0f : 2.0f), col.getRGB());
        GLUtils.postState();
    }

    private void arraylist() {
        ScaledResolution sr = RenderUtils.getResolution();
        int yPos = sr.getScaledHeight() - 12;
        for (Cheat cheat : Client.INSTANCE.getCheatManager().getCheatsForRendering()) {
            if (cheat.animationX() > 0 && cheat.visible()) {
                int width = sr.getScaledWidth() - cheat.animationX() - 4;
                int color = (o.rainbow.getValue() ? GLUtils.rainbowColor(yPos / 2, 0.9F).getRGB()
                        : Client.INSTANCE.color.getRGB());

                GLUtils.preState();
                Gui.drawRect(width - 3, yPos + 11, sr.getScaledWidth(), yPos - 1, new Color(25, 25, 25, 235).getRGB());
                Gui.drawRect(width - 2.2, yPos + 11, width - 3, yPos - 1, color);
                Gui.drawRect(sr.getScaledWidth(), yPos + 11, sr.getScaledWidth() - 0.8, yPos - 1, color);
                Client.INSTANCE.getFontManager().c16.drawStringWithShadow(Client.INSTANCE.getStyler().styleString(cheat.label(), false), width, yPos + 2.5F,
                        color);
                GLUtils.postState();
                yPos -= 12;
            }
            if (cheat.animationX() < cheat.width() && cheat.getState()) {
                if (time.hasReached(15)) {
                    cheat.setAnimationX(cheat.animationX() + 1);
                    time.reset();
                }
            } else if (cheat.animationX() > 0 && !cheat.getState()) {
                cheat.setAnimationX(cheat.animationX() - 2);
            }
        }

    }

    private void information() {
        long ms = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date d = new Date(ms);
        String date = sdf.format(d);
        boolean german = o.german.getValue();
        FontManager fm = Client.INSTANCE.getFontManager();
        CFontRenderer font = fm.c18;

        int x = 4, y = RenderUtils.getResolution().getScaledHeight() - 12;

        long xPos = Math.round(mc.thePlayer.posX), yPos = Math.round(mc.thePlayer.posY),
                zPos = Math.round(mc.thePlayer.posZ);
        if (!german) {
            GLUtils.preState();
            if (mc.currentScreen instanceof GuiChat && yAnim < 13)
                yAnim += 0.08;
            if (!(mc.currentScreen instanceof GuiChat))
                if (yAnim >= 0)
                    yAnim -= 0.08;
            font.drawStringWithShadow("FPS: \247f" + mc.debugFPS + "\247r X: \247f" + xPos + "\247r Y: \247f" + yPos
                    + "\247r Z: \247f" + zPos + "\247r Time: \247f" + date, x, y + 1 - yAnim, Client.INSTANCE.color.getRGB());

            GLUtils.postState();
        } else if (!(mc.currentScreen instanceof GuiChat)) {
            GLUtils.preState();
            font = fm.c14;
            y = y + 4;
            x = x + 12;
            font.drawStringWithShadow("FPS: \247f" + mc.debugFPS + "\247r Time: \247f" + date, x, y - 7, Client.INSTANCE.color.getRGB());
            font.drawStringWithShadow("X: \247f" + xPos + "\247r Y: \247f" + yPos + "\247r Z: \247f" + zPos, x, y,
                    Client.INSTANCE.color.getRGB());

            GLUtils.postState();
        }
    }

    private void armor() {
        if (mc.playerController.isNotCreative()) {
            int x = 14;
            int width = RenderUtils.getResolution().getScaledWidth() / 2;
            int height = RenderUtils.getResolution().getScaledHeight()
                    - (mc.thePlayer.isInsideOfMaterial(Material.water)
                    ? mc.thePlayer.getActivePotionEffect(Potion.absorption) != null ? 55 : 65
                    : 55);
            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.disableBlend();
            GLUtils.preState();

            for (int index = 3; index >= 0; index--) {
                ItemStack stack = mc.thePlayer.inventory.armorInventory[index];

                if (stack != null) {
                    RenderHelper.enableStandardItemLighting();
                    mc.getRenderItem().renderItemAndEffectIntoGUI(stack, width + x, height);
                    mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, stack, width + x, height);
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.disableDepth();
                    GlStateManager.depthMask(false);
                    GlStateManager.scale(0.50F, 0.50F, 0.50F);

                    if (stack.getItem() == Items.golden_apple && stack.hasEffect()) {
                        cf.drawString("god", (width + x) * 2, height * 2, 0xFFFF0000);
                    } else {
                        GLUtils.preState();
                        NBTTagList enchants = stack.getEnchantmentTagList();

                        if (enchants != null) {
                            int encY = 0;
                            Enchantment[] important = new Enchantment[]{Enchantment.field_180310_c,
                                    Enchantment.unbreaking, Enchantment.field_180314_l, Enchantment.fireAspect,
                                    Enchantment.efficiency, Enchantment.field_180309_e, Enchantment.power,
                                    Enchantment.flame, Enchantment.punch, Enchantment.fortune, Enchantment.infinity,
                                    Enchantment.thorns};

                            if (enchants.tagCount() >= 6) {
                                cf.drawString("god", (width + x) * 2, height * 2, 0xFFFF0000);
                            } else {
                                for (int index1 = 0; index1 < enchants.tagCount(); ++index1) {
                                    short id = enchants.getCompoundTagAt(index1).getShort("id");
                                    short level = enchants.getCompoundTagAt(index1).getShort("lvl");
                                    Enchantment enc = Enchantment.func_180306_c(id);

                                    if (enc != null) {
                                        for (Enchantment importantEnchantment : important) {
                                            if (enc == importantEnchantment) {
                                                String encName = enc.getTranslatedName(level).substring(0, 1)
                                                        .toLowerCase();

                                                if (level > 99) {
                                                    encName = encName + "99+";
                                                } else {
                                                    encName = encName + level;
                                                }

                                                cf.drawString(encName, (width + x) * 2, (height + encY) * 2,
                                                        0xFFAAAAAA);
                                                encY += 5;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    GLUtils.postState();
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.scale(2.0F, 2.0F, 2.0F);
                    x += 18;
                }
            }

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            GLUtils.postState();
        }

    }

    private void potions() {
        int y = 50;
        Gui gui = new Gui();
        Collection collection = this.mc.thePlayer.getActivePotionEffects();

        if (!collection.isEmpty()) {
            for (Object o : this.mc.thePlayer.getActivePotionEffects()) {
                PotionEffect potioneffect = (PotionEffect) o;
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                double minutes = -1;
                double seconds = -2;
                String time = Potion.getDurationString(potioneffect), multiplier = "";

                try {
                    minutes = Integer.parseInt(Potion.getDurationString(potioneffect).split(":")[0]);
                    seconds = Integer.parseInt(Potion.getDurationString(potioneffect).split(":")[1]);
                } catch (Exception e) {
                    time = "**:**";
                }

                if (potioneffect.getAmplifier() == 1) {
                    multiplier = "II";
                } else if (potioneffect.getAmplifier() == 2) {
                    multiplier = "III";
                } else if (potioneffect.getAmplifier() == 3) {
                    multiplier = "IV";
                }

                int color = Client.INSTANCE.color.getRGB();

                if (potioneffect.getDuration() > Integer.MAX_VALUE - 1 && potioneffect.getAmplifier() == 255)
                    continue;

                String name = I18n.format(potion.getName()) + "\2477 " + time;
                GLUtils.preState();
                Client.INSTANCE.getFontManager().c16.drawStringWithShadow(name,
                        (RenderUtils.getResolution().getScaledWidth() - 4
                                - Client.INSTANCE.getFontManager().c16.getStringWidth(name)),
                        (RenderUtils.getResolution().getScaledHeight() - y + 40
                                - (!(mc.currentScreen instanceof GuiChat) && Horizon.o.german.getValue() ? 20 : 0)),
                        color);
                GLUtils.postState();
                y += 12;
            }
        }
    }

}
