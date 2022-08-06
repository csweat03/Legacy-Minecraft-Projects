package club.shmoke.client.ui.click.themes.horizon.elements.other;

import java.util.ArrayList;
import java.util.List;

import club.shmoke.client.util.render.AnimationUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import club.shmoke.client.Client;
import club.shmoke.client.cheats.visual.ClickGui;
import club.shmoke.client.ui.click.Element;
import club.shmoke.client.ui.click.GuiClick;
import club.shmoke.client.util.render.GLUtils;
import club.shmoke.client.util.render.RenderUtils;

public class Panel extends Element {

	public String category;
	public List<Element> elements = new ArrayList<>();
	public int maxHeight = 0;

	public boolean dragging;
	public float newXd;
	public float newYd;

	protected int fadeTicks = 0;
	protected boolean fade = false;

	public boolean minimized = false;
	protected boolean maximizing = false;
	protected boolean minimizing = false;
	protected float minimizeFade = 0;

	public Panel(String category) {
		this.category = category;
	}

	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0) {
			this.dragging = false;
		}
		for (Element elButton : elements) {
			elButton.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		for (Element e : elements) {
			e.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	public void drawElement(float posX, float posY, int mouseX, int mouseY, float partialTicks) {
		ClickGui gui = (ClickGui) Client.INSTANCE.getCheatManager().get(ClickGui.class);
		maxHeight = gui.scrollbarLength.getValue();
		boolean isOverAll = mouseX > getPosX() && mouseY > getPosY() && mouseX < getPosX() + getWidth()
				&& mouseY < getPosY() + getHeight();

		if (dragging) {
			setPosX(mouseX + newXd);
			setPosY(mouseY + newYd);
		}

		double speed = 0;
		if (isOverAll) {
			speed = Math.round(scrollAmount / 8);
			if (scrollAmount > 0)
				speed = speed * 2;
			if (scrollAmount > 0) {
				if (scrollAmount - speed <= 0)
					scrollAmount = 0;
				else
					scrollAmount -= speed;
			} else if (scrollAmount < 0) {
				if (scrollAmount - speed >= 0)
					scrollAmount = 0;
				else
					scrollAmount -= speed;
			}

			if (scrollAmount != 0) {
				translateY += 30 / scrollAmount;
			}

			if (translateY < (-getHeight() + maxHeight) && getHeight() >= maxHeight) {
				translateY = (int) (-getHeight() + maxHeight);
				scrollAmount = 0;
			}
			if (translateY > 0) {
				translateY = 0;
				scrollAmount = 0;
			}
		} else {
			scrollAmount = 0;
		}
		if (speed == 0)
			scrollAmount = 0;

		boolean overMinimize = mouseX > getPosX() + getWidth() - 12 && mouseY > getPosY()
				&& mouseX < getPosX() + getWidth() && mouseY < getPosY() + 14;

		if (!minimizing && !maximizing)
			RenderUtils.prepareScissorBox((int) getPosX(), (int) getPosY() - 3, (int) getPosX() + this.getWidth(),
					(int) (getPosY() + maxHeight));
		else {
			RenderUtils.prepareScissorBox((int) getPosX(), (int) getPosY() - 3, (int) getPosX() + this.getWidth(),
					(int) (getPosY() + minimizeFade));
		}
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		double realHeight = getHeight() < maxHeight ? getHeight() : maxHeight - 1;
		if (!minimized)
			Gui.drawBorderedGradientRect(getPosX(), getPosY() + 12.2, getPosX() + getWidth(), getPosY() + realHeight, 2,
					Client.INSTANCE.color.getRGB(), 0xdd111111,
					Client.INSTANCE.color.darker().darker().darker().getRGB());
		String name = WordUtils.capitalizeFully(category.toUpperCase());
		GLUtils.preState();

		RenderUtils.drawGradientRect(getPosX(), getPosY() - 5, getPosX() + getWidth(), getPosY() + 14,
				Client.INSTANCE.color.getRGB(), Client.INSTANCE.color.darker().darker().darker().getRGB());

		Client.INSTANCE.getFontManager().c18.drawStringWithShadow(name,
				((getPosX() + getWidth() / 2) - Client.INSTANCE.getFontManager().c18.getStringWidth(name) / 2),
				getPosY() + 1.5f, 0xFFFFFFFF);
		GLUtils.postState();

		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		
		RenderUtils.prepareScissorBox((int) getPosX(), (int) getPosY() - 3, (int) getPosX() + this.getWidth(),
				(int) (getPosY() + maxHeight - 1));

		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		if (!minimized) {
			if (getHeight() >= maxHeight && !minimizing && !maximizing) {
				Gui.drawRect(getPosX() + getWidth() - 3, getPosY() + 14, getPosX() + getWidth() - 1,
						getPosY() + maxHeight - 1, 0x22FFFFFF);

				float pval = (-translateY * 100 / ((getHeight() - maxHeight) + 0.001F));
				float percent = (pval * (maxHeight - 80) / 100);
				Gui.drawRect(getPosX() + getWidth() - 3, getPosY() + 14 + percent, getPosX() + getWidth() - 1,
						getPosY() + 44 + percent + 38, GuiClick.color);
			}

			GlStateManager.pushMatrix();
			int height = 0;
			for (Element elButton : elements) {
				int endY = (int) (elButton.getPosY() + elButton.getHeight());
				if (minimizing || maximizing)
					endY = (int) (endY > (getPosY() + minimizeFade) ? (getPosY() + minimizeFade) : endY);

				int startY = (int) getPosY() + 14;
				if (endY > (int) getPosY() + (maxHeight - 2))
					endY = (int) getPosY() + (maxHeight - 2);

				if (startY >= endY) {
					startY = endY;
				}
				RenderUtils.prepareScissorBox((int) elButton.getPosX(), (int) startY + 1,
						(int) elButton.getPosX() + this.getWidth(), (int) (endY + 1));
				GL11.glEnable(GL11.GL_SCISSOR_TEST);
				float butX = getPosX();
				float butY = getPosY() + 15 + height;

				elButton.drawElement(butX, butY + ((int) translateY), mouseX, mouseY, partialTicks);
				height += elButton.getHeight();
				GL11.glDisable(GL11.GL_SCISSOR_TEST);
			}
			GlStateManager.popMatrix();

			float nHeight = getHeight();
			if (nHeight > maxHeight - 1)
				nHeight = maxHeight - 1;

			boolean isOver = mouseX > getPosX() && mouseX < getPosX() + getWidth() && mouseY > getPosY() + nHeight
					&& mouseY < getPosY() + nHeight + 8;

			if (isOver) {
				fade = true;
			}

			if (fade) {
				if (fadeTicks < 15)
					fadeTicks++;
				else
					fade = false;
			} else if (fadeTicks > 0)
				--fadeTicks;

			if (getHeight() < maxHeight) {
				if (translateY < 0)
					translateY = 0;
			}
		}
		GL11.glDisable(GL11.GL_SCISSOR_TEST);

		if (minimizing && minimizeFade > 14)
			minimizeFade -= 14;
		else if (minimizing) {
			minimized = true;
			maximizing = false;
			minimizing = false;
			minimizeFade = 0;
		} else if (maximizing) {
			if (minimizeFade >= maxHeight) {
				minimized = false;
				maximizing = false;
				minimizing = false;
				minimizeFade = 0;
			} else {
				minimizeFade += 14;
			}
		}
	}

	public double scrollAmount = 0;
	public int translateY = 0;

	public void handleMouseInput(int direction) {
		if (direction != 0 && getHeight() >= maxHeight) {
			scrollAmount += direction;
		}
	}

	public void update() {
		int height = 16;
		for (Element elButton : elements) {
			height += elButton.getHeight();
		}
		setHeight(height);
	}

	public boolean isOverPanel(int mouseX, int mouseY) {
		if (minimized)
			return mouseX > getPosX() && mouseX < getPosX() + getWidth() && mouseY > getPosY()
					&& mouseY < getPosY() + 14;
		return mouseX > getPosX() && mouseX < getPosX() + getWidth() && mouseY > getPosY()
				&& mouseY < getPosY() + getHeight() + 8;
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		boolean isOverDRAG = mouseX > getPosX() && mouseY > getPosY() && mouseX < getPosX() + getWidth()
				&& mouseY < getPosY() + 14;

		boolean overMinimize = mouseX > getPosX() + getWidth() - 12 && mouseY > getPosY()
				&& mouseX < getPosX() + getWidth() && mouseY < getPosY() + 14;
		if (overMinimize && button == 0 || isOverDRAG && button == 1) {
			if (minimized) {
				maximizing = true;
				minimized = false;
				minimizing = false;
				minimizeFade = 14;
			} else {
				maximizing = false;
				minimized = false;
				minimizing = true;
				if (getHeight() > maxHeight)
					minimizeFade = maxHeight;
				else
					minimizeFade = getHeight();
			}

		} else if (isOverDRAG && button == 0) {
			this.dragging = true;
			this.newXd = (this.getPosX() - mouseX);
			this.newYd = (this.getPosY() - mouseY);
		} else {
			for (Element elButton : elements) {
				if (mouseY < (getPosY() + maxHeight))
					elButton.mouseClicked(mouseX, mouseY, button);
			}
		}
	}
}
