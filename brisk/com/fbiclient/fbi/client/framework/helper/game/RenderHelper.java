package com.fbiclient.fbi.client.framework.helper.game;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.fbiclient.fbi.client.framework.helper.IHelper;
import com.fbiclient.fbi.impl.cheats.visual.Tracers;
import me.xx.utility.render.GlRender;
import me.xx.utility.render.Interpolate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderHelper implements IHelper {

    private static Frustum FRUSTUM = new Frustum();
    public static ResourceLocation BACKGROUND = new ResourceLocation("client/background.png");

    public static ScaledResolution getRes() {
        return new ScaledResolution(mc);
    }

    public void drawTexturedRect(ResourceLocation resouce) {
        ScaledResolution sr = new ScaledResolution(mc);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resouce);
        Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1, 1, sr.getScaledWidth(), sr.getScaledHeight(), 1, 1);
    }

    public void drawRainbowRect(float left, float top, float right, float bottom) {
        double width = (right - left);
        for (double i = 0; i <= width; i++) {
            Gui.drawRect(right - i, top, right - i + 1, bottom, GlRender.getRainbow((i * 2), 0.8, 0.9).getRGB());
        }
    }

    public void drawLine(Entity target, double[] color) {
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        Minecraft.getMinecraft().entityRenderer.orientCamera(Minecraft.getMinecraft().timer.renderPartialTicks);
        GlRender.pre();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        double x = Interpolate.interpolate(target.posX, target.lastTickPosX),
                y = Interpolate.interpolate(target.posY, target.lastTickPosY),
                z = Interpolate.interpolate(target.posZ, target.lastTickPosZ);
        x -= Minecraft.getMinecraft().getRenderManager().renderPosX;
        y -= Minecraft.getMinecraft().getRenderManager().renderPosY;
        z -= Minecraft.getMinecraft().getRenderManager().renderPosZ;
        if (color.length >= 4) {
            if (color[3] <= 0.1) {
                return;
            }
            GL11.glColor4d(color[0], color[1], color[2], color[3]);
        } else {
            GL11.glColor3d(color[0], color[1], color[2]);
        }
        GL11.glLineWidth(1.5F);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            GL11.glVertex3d(0, Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0);
            GL11.glVertex3d(x, y + 2, z);
            if (Tracers.spines)
                GL11.glVertex3d(x, y + target.getEyeHeight(), z);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlRender.post();
        GL11.glPopMatrix();
    }

    public void drawBeacon(Entity target, double[] color) {
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);
        GlRender.pre();
        GL11.glEnable(2848);
        double x = Interpolate.interpolate(target.posX, target.lastTickPosX);
        double y = Interpolate.interpolate(target.posY, target.lastTickPosY);
        double z = Interpolate.interpolate(target.posZ, target.lastTickPosZ);
        x -= mc.getRenderManager().renderPosX;
        y -= mc.getRenderManager().renderPosY;
        z -= mc.getRenderManager().renderPosZ;
        if (color.length >= 4) {
            if (color[3] <= 0.1) {
                return;
            }
            GL11.glColor4d(color[0], color[1], color[2], color[3]);
        } else {
            GL11.glColor3d(color[0], color[1], color[2]);
        }
        GL11.glLineWidth(1.5f);
        GL11.glBegin(1);
        GL11.glVertex3d(0.0, (double) mc.theWorld.getActualHeight(), 0.0);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(2848);
        GlRender.post();
        GL11.glPopMatrix();
    }

    public void drawCircle(float x, float y, float radius, int color) {
        Color c = new Color(color);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.001f);
        GlStateManager.enableBlend();
        GL11.glDisable(3553);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f,
                c.getAlpha() / 255.0f);
        GL11.glBegin(9);
        for (double i = 0.0; i < 36.0; ++i) {
            double cs = i * 10.0 * 3.141592653589793 / 180.0;
            double ps = (i * 10.0 - 1.0) * 3.141592653589793 / 180.0;
            double[] outer = {Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius,
                    -Math.sin(ps) * radius};
            GL11.glVertex2d(x + outer[0], y + outer[1]);
        }
        GL11.glEnd();
        GL11.glEnable(2848);
        GL11.glBegin(3);
        for (double i = 0.0; i < 37.0; ++i) {
            double cs = i * 10.0 * 3.141592653589793 / 180.0;
            double ps = (i * 10.0 - 1.0) * 3.141592653589793 / 180.0;
            double[] outer = {Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius,
                    -Math.sin(ps) * radius};
            GL11.glVertex2d(x + outer[0], y + outer[1]);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.alphaFunc(516, 0.1f);
    }

    public static void drawXMark(float x, float y, int hexColor) {
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        hexColor(hexColor);
        GL11.glLineWidth(2.25f);
        GL11.glBegin(1);
        GL11.glVertex2d((double) x, (double) y + 6);
        GL11.glVertex2d((double) x + 6, (double) y - 1);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d((double) x, (double) y - 1);
        GL11.glVertex2d((double) x + 6, (double) y + 6);
        GL11.glEnd();
        /*GL11.glBegin(1);
        GL11.glVertex2d((double) (x + 1.0f), (double) (y + 1.0f));
        GL11.glVertex2d((double) (x + 3.0f), (double) (y + 4.0f));
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d((double) (x + 3.0f), (double) (y + 4.0f));
        GL11.glVertex2d((double) (x + 6.0f), (double) (y - 2.0f));
        GL11.glEnd();*/
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawArrow(float x, float y, float width, int hexColor) {
        GL11.glPushMatrix();
        GL11.glScaled(1.3, 1.3, 1.3);
        x /= 1.3;
        y /= 1.3;
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        hexColor(hexColor);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) (x + 3), (double) (y + 4));
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d((double) (x + 3), (double) (y + 4));
        GL11.glVertex2d((double) (x + 6), (double) y);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void hexColor(int hexColor) {
        float red = (hexColor >> 16 & 0xFF) / 255.0f;
        float green = (hexColor >> 8 & 0xFF) / 255.0f;
        float blue = (hexColor & 0xFF) / 255.0f;
        float alpha = (hexColor >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public boolean isInFrustumView(Entity ent) {
        Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        double x = Interpolate.interpolate(current.posX, current.lastTickPosX),
                y = Interpolate.interpolate(current.posY, current.lastTickPosY),
                z = Interpolate.interpolate(current.posZ, current.lastTickPosZ);
        FRUSTUM.setPosition(x, y, z);
        return FRUSTUM.isBoundingBoxInFrustum(ent.getEntityBoundingBox()) || ent.ignoreFrustumCheck;
    }

}
