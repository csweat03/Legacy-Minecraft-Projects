package club.shmoke.api.utility.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Christian
 */
public class RenderUtility {

    private ScaledResolution scaledResolution;
    private Minecraft mc = Minecraft.getMinecraft();

    public ScaledResolution getResolution() {
        return scaledResolution = new ScaledResolution(mc);
    }

    public void drawClientBackground() {

        GLUtility.enableGL();
        drawImage(new ResourceLocation("client/back.jpg"), 0, 0, getResolution().getScaledWidth(), getResolution().getScaledHeight(), true);
        GLUtility.disableGL();
    }

    public void drawImage(ResourceLocation image, int x, int y, int width, int height, boolean scale) {
        drawImage(image, x, y, width, height, new int[]{255, 255, 255, 255}, scale);
    }

    public void drawImage(ResourceLocation image, int x, int y, int width, int height, int[] color, boolean scale) {
        mc.getTextureManager().bindTexture(image);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        if (scale) {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            x *= 2;
            y *= 2;
            width *= 2;
            height *= 2;
        }
        GL11.glColor4f((float) color[0] / 255, (float) color[1] / 255, (float) color[2] / 255, (float) color[3] / 255);
        Gui.drawScaledCustomSizeModalRect(x, y, 0, 0, width, height, width, height, width, height);
        GlStateManager.popMatrix();
    }

    public ResourceLocation fetchHeadImage(EntityPlayer player) {
        ResourceLocation head = new ResourceLocation("heads/" + player.getName());
        ThreadDownloadImageData textureHead = new ThreadDownloadImageData(null, String.format("https://minotar.net/helm/%s/64.png", player.getName()), null, null);
        Minecraft.getMinecraft().getTextureManager().loadTexture(head, textureHead);
        try {
            Thread.sleep(30L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return head;
    }

    public void drawBorderRect(int left, int top, int right, int bottom, int border, int fill) {
        Gui.drawRect(left, top, right, bottom, fill);
        Gui.drawRect(left, top + 1, right + 1, top, border);
        Gui.drawRect(right, top, right + 1, bottom + 1, border);
        Gui.drawRect(left - 1, bottom + 1, right, bottom, border);
        Gui.drawRect(left - 1, top, left, bottom + 1, border);
    }

}
