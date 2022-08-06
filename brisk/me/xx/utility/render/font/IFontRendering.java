package me.xx.utility.render.font;

public interface IFontRendering {
	void drawString(String string, float x, float y, int color);

	void drawStringWithShadow(String string, float x, float y, int color);

	void drawCenteredString(String string, float x, float y, int color);
	
	float getWidth(String string);

	float getHeight(String string);
}
