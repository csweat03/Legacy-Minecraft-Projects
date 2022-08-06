package club.shmoke.client.cheats.visual.theme.themes;

import club.shmoke.api.theme.Theme;
import club.shmoke.client.cheats.visual.theme.ThemeHelper;

public class ToggleSneak extends Theme implements ThemeHelper {

	@Override
	public void render() {
		mc.fontRendererObj.drawStringWithShadow((mc.thePlayer.isSprinting() ? "\247f[Sprinting (Toggled)]" : mc.thePlayer.isSneaking() ? "\247f[Sneaking (Key Held)]" : mc.thePlayer.capabilities.isFlying ? "\247f[Flying]" : ""), 1, 1, -1);
	}

	@Override
	public void renderTab() {}
}
