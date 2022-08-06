package club.shmoke.client.cheats.visual.theme;

import net.minecraft.client.Minecraft;
import club.shmoke.client.Client;
import club.shmoke.client.cheats.visual.Overlay;
import club.shmoke.client.util.render.GLUtils;
import club.shmoke.client.util.render.RenderUtils;
import club.shmoke.client.util.render.font.FontManager;
import club.shmoke.client.util.render.font.FontManager.CFontRenderer;

public interface ThemeHelper {

	Minecraft mc = Minecraft.getMinecraft();
	Overlay o = (Overlay) Client.INSTANCE.getCheatManager().get(Overlay.class);
	CFontRenderer cf = Client.INSTANCE.getFontManager().c16;

}
