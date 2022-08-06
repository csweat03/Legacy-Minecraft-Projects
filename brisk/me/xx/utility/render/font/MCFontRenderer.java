package me.xx.utility.render.font;

import com.fbiclient.fbi.client.framework.helper.IHelper;

public class MCFontRenderer implements IFontRendering, IHelper {
	@Override
	public void drawString(final String text, final float x, final float y, final int color) {
		mc.fontRendererObj.drawString(text, x, y, color);
	}

	@Override
	public void drawStringWithShadow(final String text, final float x, final float y, final int color) {
		mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
	}

	@Override
	public float getWidth(final String text) {
		return mc.fontRendererObj.getStringWidth(text);
	}

	@Override
	public float getHeight(final String text) {
		return mc.fontRendererObj.FONT_HEIGHT;
	}

	@Override
	public void drawCenteredString(String string, float x, float y, int color) {
		mc.fontRendererObj.drawCenteredString(string, x, y, color);
	}
}
