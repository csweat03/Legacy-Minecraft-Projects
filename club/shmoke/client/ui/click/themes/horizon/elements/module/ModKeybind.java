package club.shmoke.client.ui.click.themes.horizon.elements.module;

import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.ui.click.Element;
import club.shmoke.client.ui.click.GuiClick;

public class ModKeybind extends Element {
    private ModContainer parent;
    private Cheat mod;

    public ModKeybind(ModContainer panel, Cheat m) {
        this.parent = panel;
        this.mod = m;
    }

    @Override
    public void drawElement(float posX, float posY, int mouseX, int mouseY, float partialTicks) {
    	String displayKey = "";
    	try {
    		int key = mod.macro();
    		displayKey = Keyboard.getKeyName(key);
    	}catch(Exception e) {
    		displayKey = "None";
    	}
    	String s = "\247fToggle Key: \247r" + (displayKey.equalsIgnoreCase("none") ? WordUtils.capitalizeFully(displayKey) : displayKey);
        float w = Client.INSTANCE.getFontManager().c18.getStringWidth(EnumChatFormatting.getTextWithoutFormattingCodes(s));
    	posX -= 5;
        setWidth(getWidth() + 3);
        setHeight(2);
        setPosX(posX);
        setPosY(posY);

        int uicolor = GuiClick.color;

        GlStateManager.pushMatrix();
        Client.INSTANCE.getFontManager().c14.drawStringWithShadow(s, posX + 4.5F, posY, uicolor);
        GlStateManager.popMatrix();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)  {
    }

    @Override
    public float getHeight() {
        return 8;
    }
}
