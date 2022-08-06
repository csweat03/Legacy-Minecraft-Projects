package club.shmoke.client.ui.click.themes.horizon.elements.module;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.client.ui.click.Element;

public class ModLabel extends Element {
    private ModContainer parent;
    private Cheat mod;
	private Property mv;

    public ModLabel(ModContainer panel, Cheat m, Property modValue) {
        this.parent = panel;
        this.mv = modValue;
        this.mod = m;
    }

    @Override
    public void drawElement(float posX, float posY, int mouseX, int mouseY, float partialTicks) {
    	String s = mv.label();
    	posX -= 6;
        setWidth(getWidth() + 3);
        setHeight(12);
        setPosX(posX);
        setPosY(posY);

        GlStateManager.pushMatrix();
        Client.INSTANCE.getFontManager().c14.drawStringWithShadow(s, posX + 4.5F, posY + 3, -1);
        Gui.drawRect(posX + 4, posY + 9, posX + getWidth() - 3, posY + 10, -1);
        GlStateManager.popMatrix();
    }
}
