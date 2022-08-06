package club.shmoke.client.ui.click.themes.horizon.elements.module;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.util.ResourceLocation;
import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.ui.click.Element;
import club.shmoke.client.ui.click.GuiClick;
import club.shmoke.client.ui.click.themes.horizon.elements.other.Panel;
import club.shmoke.client.util.render.GLUtils;

public class ModButton extends Element {

	private Cheat mod;
	private List<ModButton> modList = new ArrayList<>();
	public ModContainer container = null;
	public int height = 20;

	private int key;
	private boolean changingMacro = false;

	public ModButton(Panel panel, Cheat mod) {
		this.parent = panel;
		this.mod = mod;
		this.setWidth(100);
		this.setHeight(height);

		container = new ModContainer(this, mod);

	}

	private ResourceLocation arrow = new ResourceLocation("solar/tick.png");

	public Panel parent;

	private int tickFade = 0;
	private float tickButton = 0;
	private boolean fade = false;

	@Override
	public void drawElement(float posX, float posY, int mouseX, int mouseY, float partialTicks) {
		this.setPosX(posX);
		this.setPosY(posY);

		if ((posY + getRealHeight()) < (parent.getPosY() + 14))
			return;
		try {
			boolean hasValues = true;
			boolean isOverValues = hasValues && (mouseX > getPosX() + 2 && mouseX <= getPosX() + 13
					&& mouseY > getPosY() && mouseY <= getPosY() + getHeight());
			float overX = getPosX() + (isOverValues ? 10 : 0);
			boolean isOver = mouseX > overX && mouseX <= overX + getWidth() && mouseY > getPosY()
					&& mouseY <= getPosY() + getHeight();
			boolean enabled = mod.getState();

			int colorIfOver = 0xFF151515;
			int tickSpeed = 5;
			if (isOver) {
				fade = true;
			}

			if (fade) {
				if (tickFade < tickSpeed)
					tickFade++;
				else
					fade = false;
			} else if (tickFade > 0) {
				--tickFade;
			}

			if (tickFade != 0) {
				int multiplier = 75 / tickSpeed;
				colorIfOver = ((tickFade * multiplier) << 24) | colorIfOver;
			} else
				colorIfOver = 0;

			boolean flag = false;

			int color1 = flag ? 0x1F000000 : 0x5F000000;
			int colorIf = flag ? 0 : colorIfOver;
			int uicolor = GuiClick.color;
			GLUtils.preState();
			Client.INSTANCE.getFontManager().c18.drawCenteredStringWithShadow(changingMacro ? ". . ." : mod.label(),
					getPosX() + getWidth() / 2, getPosY() + 6, flag ? 0xFF6F6F6F : (enabled ? uicolor : 0xFFFFFFFF));
			GLUtils.postState();

			if (container != null)
				container.drawElement(getPosX() + 1, getPosY() + 20, mouseX, mouseY, partialTicks);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		if (changingMacro
				&& (keyCode == Keyboard.KEY_DELETE || keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_BACK)) {
			mod.setMacro(0);
			changingMacro = false;
		} else if (changingMacro) {
			mod.setMacro(keyCode);
			changingMacro = false;
		}
		if (container != null)
			container.keyTyped(typedChar, keyCode);
	}

	@Override
	public float getRealHeight() {
		return container == null ? 20 : ((container.isExpanded ? container.getHeight() - 4 : 0) + 5);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		try {
			boolean hasValues = true;
			boolean isOverValues = hasValues && (mouseX > getPosX() + 2 && mouseX <= getPosX() + 2 && mouseY > getPosY()
					&& mouseY <= getPosY() + getHeight());
			float overX = getPosX() + (isOverValues ? 10 : 0);

			boolean isOver = mouseX > overX && mouseX <= overX + getWidth() && mouseY > getPosY()
					&& mouseY <= getPosY() + 20;

			if (button == 0 && isOverValues || button == 1 && isOver) {
				if (getHeight() > 20) {
					container.moveUp = true;
					container.moveTicks = container.maxTicks;
				} else {
					container.isExpanded = true;
					container.moveDown = true;
				}

			} else if (button == 0 && isOver) {
				mod.toggle();
			} else if (button == 2 && isOver) {
				changingMacro = !changingMacro;
			}
			if (container != null)
				container.mouseClicked(mouseX, mouseY, button);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int button) {
		if (container != null)
			container.mouseReleased(mouseX, mouseY, button);
	}
}
