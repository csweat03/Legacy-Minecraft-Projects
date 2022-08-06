package me.xx.utility.render;

import java.awt.Color;
import java.util.Stack;

import org.lwjgl.opengl.GL11;

import com.fbiclient.fbi.client.framework.helper.IHelper;
import me.xx.utility.MathUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

/**
 * @author Kyle
 * @since 2/12/2018
 **/
public class GlRender implements IHelper {

    private static Stack<Integer> functionStack = new Stack<>();

    public static void preState() {
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public static void postState() {
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

    public static void pre() {
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
    }

    public static void post() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glColor3d(1.0, 1.0, 1.0);
    }

    /**
     * @return Constantly updating rainbow color
     * @author Vadim Lelikov
     */
    public static Color getRainbow(double offset, double brightness, double alpha) {
        float hue = ((int) (((System.currentTimeMillis() / 20) + offset) % 256));
        return Color.getHSBColor((hue / 255), (float) brightness, (float) alpha);
    }

    /**
     * @return Random generated color
     * @author Vadim Leliko
     */
    public static int getRandomRGB(double min, double max, float alpha) {
        return new Color((float) MathUtility.getRandom(min, max), (float) MathUtility.getRandom(min, max),
                (float) MathUtility.getRandom(min, max), alpha).getRGB();
    }

    /**
     * Prepares a GL scissor box
     */
    public static void prepareScissorBox(float x, float y, float x2, float y2) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        GL11.glScissor((int) (x * factor), (int) ((scale.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor),
                (int) ((y2 - y) * factor));
    }
    
    public static void drawCustomImage(int x, int y, int width, int height, ResourceLocation image){
		double par1 = x + width;
		double par2 = y + height;
    	GL11.glDisable(GL11.GL_DEPTH_TEST);
    	GL11.glDepthMask(false);
    	OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
    	Minecraft.getMinecraft().getTextureManager().bindTexture(image);
    	Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
    	GL11.glDepthMask(true);
    	GL11.glEnable(GL11.GL_DEPTH_TEST);
    	GL11.glEnable(GL11.GL_ALPHA_TEST);
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

    //Mask Rendering

    public static void begin() {
        if (!functionStack.empty()) {
            return;
        }
        GlStateManager.clearDepth(1.0);
        GlStateManager.clear(256);
    }

    public static void end() {
        if (functionStack.isEmpty()) {
            return;
        }
        GlStateManager.depthFunc(functionStack.pop());
        GlStateManager.clear(256);
    }

    public static void prepareMaskRender() {
        functionStack.push(GL11.glGetInteger(2932));
        GlStateManager.colorMask(false, false, false, false);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(513);
    }

    public static void beginDefaultRender() {
        GlStateManager.depthFunc(514);
        GlStateManager.colorMask(true, true, true, true);
    }

}
