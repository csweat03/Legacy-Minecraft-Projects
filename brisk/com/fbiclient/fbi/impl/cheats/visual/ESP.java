package com.fbiclient.fbi.impl.cheats.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import me.xx.api.cheat.Category;
import me.xx.api.cheat.Cheat;
import me.xx.api.cheat.CheatManifest;
import me.xx.api.event.Register;
import com.fbiclient.fbi.client.events.render.RenderEntityEvent;
import com.fbiclient.fbi.client.events.render.RenderGuiEvent;
import me.xx.utility.render.GlColor;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle
 * @since 5/1/2018
 **/
@CheatManifest(label = "ESP", category = Category.VISUAL, description = "Renders visuals around entities through walls")
public class ESP extends Cheat {

    public static Map<EntityLivingBase, double[]> entityPositionstop = new HashMap<>();
    public static Map<EntityLivingBase, double[]> entityPositionsbottom = new HashMap<>();

    private double gradualFOVModifier;

    @Register
    public void preRender(RenderEntityEvent event) {
        setSuffix("2D");
        this.updatePositions();
    }

    @Register
    public void twod(RenderGuiEvent event) {
        GlStateManager.pushMatrix();
        ScaledResolution scaledRes = new ScaledResolution(mc);
        double twoDscale = (double) scaledRes.getScaleFactor() / Math.pow(scaledRes.getScaleFactor(), 2.0);
        GlStateManager.scale(twoDscale, twoDscale, twoDscale);
        for (Entity ent : entityPositionstop.keySet()) {
            double[] renderPositions = entityPositionstop.get(ent);
            double[] renderPositionsBottom = entityPositionsbottom.get(ent);
            if (renderPositions[3] <= 0.0 && renderPositions[3] > 1.0)
                continue;
            GlStateManager.pushMatrix();
            if (!ent.isInvisible() && ent instanceof EntityPlayer && !(ent instanceof EntityPlayerSP)) {
                this.scale();
                try {
                    ItemStack stack4, stack5, stack3;
                    float y = (float) renderPositions[1];
                    float endy = (float) renderPositionsBottom[1];
                    float meme = endy - y;
                    float x = (float) renderPositions[0] - meme / 4.0f;
                    float endx = (float) renderPositionsBottom[0] + meme / 4.0f;
                    if (x > endx) {
                        endx = x;
                        x = (float) renderPositionsBottom[0] + meme / 4.0f;
                    }
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(2.0f, 2.0f, 2.0f);
                    GlStateManager.popMatrix();
                    GL11.glEnable((int) 3042);
                    GL11.glDisable((int) 3553);
                    int color = getColor((EntityLivingBase) ent);
                    Gui.drawBorderedRect(x, y, endx, endy, 2.25, GlColor.getColor(0, 0, 0, 0), color);
                    Gui.drawBorderedRect((double) x - 0.5, (double) y - 0.5, (double) endx + 0.5, (double) endy + 0.5,
                            0.9, GlColor.getColor(0, 0), GlColor.getColor(0));
                    Gui.drawBorderedRect((double) x + 2.5, (double) y + 2.5, (double) endx - 2.5, (double) endy - 2.5,
                            0.9, GlColor.getColor(0, 0), GlColor.getColor(0));
                    Gui.drawBorderedRect(x - 5.0f, y - 1.0f, x - 1.0f, endy, 1.0, GlColor.getColor(0, 100), GlColor.getColor(0, 255));
                    float var1 = (endy - y) / 4.0f;
                    ItemStack stack2 = ((EntityPlayer) ent).getEquipmentInSlot(4);
                    if (stack2 != null) {
                        Gui.drawBorderedRect(endx + 1.0f, y + 1.0f, endx + 6.0f, y + var1, 1.0,
                                GlColor.getColor(28, 156, 179, 100), GlColor.getColor(0, 255));
                        float diff1 = y + var1 - 1.0f - (y + 2.0f);
                        double max = stack2.getMaxDamage() < 1 ? 1 : stack2.getMaxDamage();
                        double percent = 1.0 - (double) (stack2.getItemDamage() / max);
                        Gui.drawRect(endx + 2.0f, y + var1 - 1.0f, endx + 5.0f,
                                (double) (y + var1 - 1.0f) - (double) diff1 * percent, GlColor.getColor(78, 206, 229));
                        mc.fontRendererObj.drawStringWithShadow(
                                "" + (stack2.getMaxDamage() - stack2.getItemDamage()) + "", endx + 7.0f,
                                y + var1 - 1.0f - diff1 / 2.0f - (float) (mc.fontRendererObj.FONT_HEIGHT / 2), -1);
                    }
                    if ((stack3 = ((EntityPlayer) ent).getEquipmentInSlot(3)) != null) {
                        Gui.drawBorderedRect(endx + 1.0f, y + var1, endx + 6.0f, y + var1 * 2.0f, 1.0,
                                GlColor.getColor(28, 156, 179, 100), GlColor.getColor(0, 255));
                        float diff2 = y + var1 * 2.0f - (y + var1 + 2.0f);
                        double percent2 = 1.0
                                - (double) stack3.getItemDamage() * 1.0 / (double) stack3.getMaxDamage();
                        Gui.drawRect(endx + 2.0f, y + var1 * 2.0f, endx + 5.0f,
                                (double) (y + var1 * 2.0f) - (double) diff2 * percent2, GlColor.getColor(78, 206, 229));
                        mc.fontRendererObj.drawStringWithShadow(
                                "" + (stack3.getMaxDamage() - stack3.getItemDamage()) + "", endx + 7.0f,
                                y + var1 * 2.0f - diff2 / 2.0f - (float) (mc.fontRendererObj.FONT_HEIGHT / 2), -1);
                    }
                    if ((stack4 = ((EntityPlayer) ent).getEquipmentInSlot(2)) != null) {
                        Gui.drawBorderedRect(endx + 1.0f, y + var1 * 2.0f, endx + 6.0f, y + var1 * 3.0f, 1.0,
                                GlColor.getColor(28, 156, 179, 100), GlColor.getColor(0, 255));
                        float diff3 = y + var1 * 3.0f - (y + var1 * 2.0f + 2.0f);
                        double percent3 = 1.0
                                - (double) stack4.getItemDamage() * 1.0 / (double) stack4.getMaxDamage();
                        Gui.drawRect(endx + 2.0f, y + var1 * 3.0f, endx + 5.0f,
                                (double) (y + var1 * 3.0f) - (double) diff3 * percent3, GlColor.getColor(78, 206, 229));
                        mc.fontRendererObj.drawStringWithShadow(
                                "" + (stack4.getMaxDamage() - stack4.getItemDamage()) + "", endx + 7.0f,
                                y + var1 * 3.0f - diff3 / 2.0f - (float) (mc.fontRendererObj.FONT_HEIGHT / 2), -1);
                    }
                    if ((stack5 = ((EntityPlayer) ent).getEquipmentInSlot(1)) != null) {
                        Gui.drawBorderedRect(endx + 1.0f, y + var1 * 3.0f, endx + 6.0f, y + var1 * 4.0f, 1.0,
                                GlColor.getColor(28, 156, 179, 100), GlColor.getColor(0, 255));
                        float diff4 = y + var1 * 4.0f - (y + var1 * 3.0f + 2.0f);
                        double percent4 = 1.0
                                - (double) stack5.getItemDamage() * 1.0 / (double) stack5.getMaxDamage();
                        Gui.drawRect(endx + 2.0f, y + var1 * 4.0f - 1.0f, endx + 5.0f,
                                (double) (y + var1 * 4.0f) - (double) diff4 * percent4, GlColor.getColor(78, 206, 229));
                        mc.fontRendererObj.drawStringWithShadow(
                                "" + (stack5.getMaxDamage() - stack5.getItemDamage()) + "", endx + 7.0f,
                                y + var1 * 4.0f - diff4 / 2.0f - (float) (mc.fontRendererObj.FONT_HEIGHT / 2), -1);
                    }
                    float health = ((EntityPlayer) ent).getHealth();
                    float[] fractions = new float[]{0.0f, 0.5f, 1.0f};
                    Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
                    float progress = health * 5.0f * 0.01f;
                    Color custoMColor = GlColor.blendColors(fractions, colors, progress).brighter();
                    double healthLocation = endy + (y - endy) * (health * 5.0f * 0.01f);
                    Gui.drawRect(x - 4.0f, endy - 1.0f, x - 2.0f, healthLocation, custoMColor.getRGB());
                    if ((int) getIncremental(health * 5.0f, 1.0) != 100) {
                        GlStateManager.pushMatrix();
                        GlStateManager.scale(2.0f, 2.0f, 2.0f);
                        String healthstr = "" + (int) getIncremental(health * 5.0f, 1.0) + "HP";
                        SMALLER.drawStringWithShadow(healthstr, (x - 5.0f - SMALLER.getWidth(healthstr) * 2.0f) / 2.0f,
                                ((float) ((int) healthLocation) + SMALLER.getHeight(healthstr) / 2.0f) / 2.0f, -1);
                        GlStateManager.popMatrix();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            GlStateManager.popMatrix();
            GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        }
        GL11.glScalef((float) 1.0f, (float) 1.0f, (float) 1.0f);
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        GlStateManager.popMatrix();
    }

    public int getColor(EntityLivingBase ent) {
        int color = new Color(255, 255, 255).getRGB();
        if (ent.hurtTime > 5) {
            color = new Color(220, 55, 43).getRGB();
        } else {
            if (ent.isInvisible()) {
                color = new Color(254, 255, 71).getRGB();
            }
        }
        return color;
    }

    public static double getIncremental(double val, double inc) {
        double one = 1.0 / inc;
        return (double) Math.round(val * one) / one;
    }

    private void updatePositions() {
        entityPositionstop.clear();
        entityPositionsbottom.clear();
        float pTicks = mc.timer.renderPartialTicks;
        for (Object o : mc.theWorld.getLoadedEntityList()) {
            if (!(o instanceof EntityPlayer))
                continue;
            EntityPlayer ent = (EntityPlayer) o;
            double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double) pTicks
                    - mc.getRenderManager().viewerPosY;
            double x = ent.lastTickPosX + (ent.posX + 10.0 - (ent.lastTickPosX + 10.0)) * (double) pTicks
                    - mc.getRenderManager().viewerPosX;
            double z = ent.lastTickPosZ + (ent.posZ + 10.0 - (ent.lastTickPosZ + 10.0)) * (double) pTicks
                    - mc.getRenderManager().viewerPosZ;
            double[] convertedPoints = this.projectTo2D(x, y += (double) ent.height + 0.2, z);
            double xd = Math.abs(this.projectTo2D(x, y + 1.0, z)[1] - this.projectTo2D(x, y, z)[1]);
            assert (convertedPoints != null);
            if (convertedPoints[2] < 0.0 || convertedPoints[2] >= 1.0)
                continue;
            entityPositionstop.put(ent,
                    new double[]{convertedPoints[0], convertedPoints[1], xd, convertedPoints[2]});
            y = ent.lastTickPosY + (ent.posY - 2.2 - (ent.lastTickPosY - 2.2)) * (double) pTicks
                    - mc.getRenderManager().viewerPosY;
            entityPositionsbottom.put(ent, new double[]{this.projectTo2D(x, y, z)[0], this.projectTo2D(x, y, z)[1],
                    xd, this.projectTo2D(x, y, z)[2]});
        }
    }

    private void scale() {
        float scale = 1.0f;
        float target = scale * (mc.gameSettings.fovSetting / mc.gameSettings.fovSetting);
        if (this.gradualFOVModifier == 0.0 || Double.isNaN(this.gradualFOVModifier)) {
            this.gradualFOVModifier = target;
        }
        this.gradualFOVModifier += ((double) target - this.gradualFOVModifier) / ((double) Minecraft.debugFPS * 0.7);
        GlStateManager.scale(scale, scale, scale *= (float) this.gradualFOVModifier);
    }

    private double[] projectTo2D(double x, double y, double z) {
        FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        boolean result = GLU.gluProject(((float) x), ((float) y), ((float) z),
                modelView, projection, viewport, screenCoords);
        if (result) {
            return new double[]{screenCoords.get(0), (float) Display.getHeight() - screenCoords.get(1),
                    screenCoords.get(2)};
        }
        return null;
    }

}
