package club.shmoke.client.ui.click.themes.horizon.elements.config;

import club.shmoke.client.Client;
import club.shmoke.client.preset.Config;
import club.shmoke.client.ui.click.Element;
import club.shmoke.client.ui.click.themes.horizon.elements.other.Panel;
import club.shmoke.client.util.render.GLUtils;

public class ConfigButton extends Element {

	private Config cfg;
	public ConfigContainer container = null;
	public int height = 20;

	public ConfigButton(Panel panel, Config cfg) {
		this.parent = panel;
		this.cfg = cfg;
		this.setWidth(100);
		this.setHeight(height);
		container = new ConfigContainer(this);

	}

	public Panel parent;

	@Override
	public void drawElement(float posX, float posY, int mouseX, int mouseY, float partialTicks) {
		this.setPosX(posX);
		this.setPosY(posY);

		GLUtils.preState();
		Client.INSTANCE.getFontManager().c18.drawStringWithShadow(cfg.label(), getPosX() + 5, getPosY() + 6,
				cfg.isCurrent() ? Client.INSTANCE.color.getRGB() : 0xFFFFFFFF);
		GLUtils.postState();

		if (container != null)
			container.drawElement(getPosX() + 1, getPosY() + 20, mouseX, mouseY, partialTicks);
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		if (container != null)
			container.keyTyped(typedChar, keyCode);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		float overX = getPosX();

		boolean isOver = mouseX > overX && mouseX <= overX + getWidth() && mouseY > getPosY()
				&& mouseY <= getPosY() + 20;
		if (button == 0 && isOver)
			cfg.setCurrent();
		if (container != null)
			container.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int button) {
		if (container != null)
			container.mouseReleased(mouseX, mouseY, button);
	}
}
