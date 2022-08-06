package club.shmoke.api.utility.utilities;

import net.minecraft.client.renderer.GlStateManager;

/**
 * @author Christian
 */
public class GLUtility {

    public static void enableGL() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void disableGL() {
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
