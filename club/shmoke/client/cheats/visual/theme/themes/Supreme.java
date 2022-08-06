package club.shmoke.client.cheats.visual.theme.themes;

import club.shmoke.api.theme.Theme;
import club.shmoke.client.Client;
import club.shmoke.client.cheats.visual.theme.ThemeHelper;
import club.shmoke.client.ui.overlay.tab.HorizonTab;
import club.shmoke.client.util.render.GLUtils;
import club.shmoke.client.util.render.RenderUtils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Supreme extends Theme implements ThemeHelper {

    float alpha = 1;

    @Override
    public void render() {
        if (o.watermark.getValue()) {
            if (HorizonTab.delay.hasReached(2500) && alpha > 0.25F)
                alpha -= 0.05;
            else if (HorizonTab.delay.hasReached(2500)) alpha = 0.25F;
            else if (alpha < 1) alpha += 0.05;

            GLUtils.preState();
            GL11.glColor4f(1, 1, 1, alpha);
            RenderUtils.drawCustomImage(1, 1F, 150 / 2.03, 52 / 2.03, new ResourceLocation("client/watermark/supreme.png"));
            GLUtils.postState();
        }
    }

    @Override
    public void renderTab() {
        Client.INSTANCE.getTab().onRender(1, o.watermark.getValue() ? 27 : 2, 20, 14,
                new Color(228, 37, 32).getRGB(),
                new Color(255, 100, 100).getRGB());
    }
}
