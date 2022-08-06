package club.shmoke.client.ui.click.themes.horizon.elements.module;

import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.client.ui.click.Element;
import club.shmoke.client.ui.click.GuiClick;
import club.shmoke.client.util.render.GLUtils;

public class ModMode extends Element {

	private ModContainer parent;
	private Cheat mod;
	private Property mv;
	private boolean hoveringLeft, hoveringRight;

	private Enum[] modes;
	private int index;

	public ModMode(ModContainer panel, Cheat m, Property modValue) {
		this.parent = panel;
		this.mod = m;
		this.mv = modValue;
		this.setHeight(24);
	}

	@Override
	public void drawElement(float posX, float posY, int mouseX, int mouseY, float partialTicks) {
		posX -= 5;
		this.setWidth(this.getWidth() + 3);
		this.setPosX(posX);
		this.setPosY(posY);
		GlStateManager.pushMatrix();
		Client.INSTANCE.getFontManager().c14.drawStringWithShadow(mv.label(), posX + 5, posY + 4, 0xFFCFCFCF);
		GlStateManager.popMatrix();
		posX += 5;
		hoveringLeft = mouseX >= posX + Client.INSTANCE.getFontManager().c14.getStringWidth(mv.label) + 1
				&& mouseX <= posX + Client.INSTANCE.getFontManager().c14.getStringWidth(mv.label) + 3
				&& mouseY >= posY + 1.5F && mouseY <= posY + 11F;
		hoveringRight = mouseX >= posX + Client.INSTANCE.getFontManager().c14.getStringWidth(mv.label) + 1 + 57
				&& mouseX <= posX + Client.INSTANCE.getFontManager().c14.getStringWidth(mv.label) + 3 + 57
				&& mouseY >= posY + 1.5F && mouseY <= posY + 11F;

		int uicolor = GuiClick.color;
		String valName = WordUtils.capitalizeFully(mv.getValue() + "");
		GLUtils.preState();
		Gui.drawRect(posX + Client.INSTANCE.getFontManager().c14.getStringWidth(mv.label) + 2, posY + 1.5F, posX + 78,
				posY + 10.5F, 0x4F000000);
		Gui.drawRect(posX + 70 + 6, posY + 1.5F, posX + 70 + 8, posY + 10.5F, hoveringRight ? uicolor : 0x2FFFFFFF);
		Gui.drawRect(posX + Client.INSTANCE.getFontManager().c14.getStringWidth(mv.label) + 2, posY + 1.5F,
				posX + Client.INSTANCE.getFontManager().c14.getStringWidth(mv.label) + 4, posY + 10.5F,
				hoveringLeft ? uicolor : 0x2FFFFFFF);

		float strWidth = Client.INSTANCE.getFontManager().c14.getStringWidth(valName) / 2;
		Client.INSTANCE.getFontManager().c14.drawCenteredStringWithShadow(valName, posX + getWidth() / 2,
				(posY + 3.8F) * 1F, 0xFFCFCFCF);
		GLUtils.postState();
	}

	@Override
	public float getHeight() {
		return 15;
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		String val = WordUtils.capitalizeFully(mv.getValue() + "");

		if (button == 0) {
			if (hoveringLeft) {
				modes = (Enum[]) mv.getValue().getClass().getEnumConstants();
				if (index != 0)
					index--;
				else
					index = modes.length - 1;
				mv.setValue(modes[index]);
			} else if (hoveringRight) {
				modes = (Enum[]) mv.getValue().getClass().getEnumConstants();
				if (index < modes.length - 1)
					index++;
				else
					index = 0;
				mv.setValue(modes[index]);
			}
		}
	}
}
