package club.shmoke.client.ui.click.themes.horizon;

import java.io.BufferedWriter;
import java.io.IOException;

import org.lwjgl.input.Mouse;

import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.Cheat.Type;
import club.shmoke.client.preset.Config;
import club.shmoke.client.ui.click.Element;
import club.shmoke.client.ui.click.GuiClick;
import club.shmoke.client.ui.click.Theme;
import club.shmoke.client.ui.click.themes.horizon.elements.config.ConfigButton;
import club.shmoke.client.ui.click.themes.horizon.elements.module.ModButton;
import club.shmoke.client.ui.click.themes.horizon.elements.other.Panel;
import club.shmoke.client.util.render.RenderUtils;

public class HorizonTheme extends Theme {

	int pheight = 0;

	public HorizonTheme() {
		super("Horizon");
	}

	@Override
	public void insert() {
		if (getPanelList().isEmpty()) {
			int i = 0;
			Panel configPanel = new Panel("Preset");
			configPanel.setPosX(RenderUtils.getResolution().getScaledWidth() - RenderUtils.getResolution().getScaledWidth() + 5);
			configPanel.setPosY(100 + (18 * 5));
			configPanel.setWidth(95);
			configPanel.minimized = true;
			int pHeight2 = 0;
			configPanel.realHeight = 0;

			for (Config c : Client.INSTANCE.getConfigManager().getContents()) {
				ConfigButton configButton = new ConfigButton(configPanel, c);
				pHeight2 += 15;
				configButton.setWidth(configPanel.getWidth());
				configPanel.elements.add(configButton);
			}
			for (Type category : Type.values()) {
				Panel modPanel = new Panel(category.name());
				modPanel.setPosX(RenderUtils.getResolution().getScaledWidth() - RenderUtils.getResolution().getScaledWidth() + 5);
				modPanel.setPosY(100 + (18 * i));
				modPanel.setWidth(95);
				modPanel.minimized = true;

				for (Cheat m : Client.INSTANCE.getCheatManager().getContents()) {
					if (m.type() != null && m.type().name().equalsIgnoreCase(modPanel.category)) {

						ModButton modButton = new ModButton(modPanel, m);

						pheight += 15;
						modButton.setWidth(modPanel.getWidth());

						modPanel.elements.add(modButton);
					}
				}

				modPanel.setHeight(16 + pheight);
				configPanel.setHeight(16 + pHeight2);
				getPanelList().add(modPanel);
				getPanelList().add(configPanel);
				++i;
			}
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		for (Element e : getPanelList()) {
			e.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for (Element e : getPanelList()) {
			Panel p = (Panel) e;
			p.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		for (Element e : getPanelList()) {
			Panel p = (Panel) e;
			p.update();
			p.drawElement(0, 0, mouseX, mouseY, partialTicks);
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		for (int i = getPanelList().size() - 1; i >= 0; i--) {
			Panel p = (Panel) getPanelList().get(i);
			if (p.isOverPanel(mouseX, mouseY)) {
				Panel toMove = (Panel) getPanelList().get(getPanelList().size() - 1);
				getPanelList().set(getPanelList().indexOf(p), toMove);
				getPanelList().set(getPanelList().size() - 1, p);
				p.mouseClicked(mouseX, mouseY, button);
				break;
			}
		}
	}

	@Override
	public void handleMouseInput() {
		int direction = Mouse.getEventDWheel();
		for (Element p : getPanelList()) {
			((Panel) p).handleMouseInput(direction);
		}
	}

	@Override
	public void updatePanel(String category, float posx, float posy, int maxh, boolean mnmzd) {
		for (Element e : getPanelList()) {
			Panel p = (Panel) e;

			if (p.category.toLowerCase().replaceAll(" ", "")
					.equalsIgnoreCase(category.toLowerCase().replaceAll(" ", ""))) {
				p.setPosX(posx);
				p.setPosY(posy);
				p.maxHeight = maxh;
				p.minimized = mnmzd;
			}
		}
	}

	@Override
	public void writeSave(BufferedWriter writer) throws IOException {
		for (Element e : GuiClick.getTheme().getPanelList()) {
			Panel p = (Panel) e;
			writer.write(p.category + " = ");
			writer.write(p.getPosX() + ", " + p.getPosY() + ", " + p.maxHeight + ", " + p.minimized);
			writer.newLine();
		}
	}
}
