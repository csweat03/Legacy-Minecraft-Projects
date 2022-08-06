package club.shmoke.client.ui.click.themes.horizon.elements.config;

import java.util.ArrayList;
import java.util.List;

import club.shmoke.client.ui.click.Element;

public class ConfigContainer extends Element {

	private ConfigButton parent;
	private List<Element> valueList = new ArrayList();
	boolean isExpanded = false;
	public int moveTicks = 0;
	public float totalHeight = 20;
	public int maxTicks = 50;
	public boolean moveDown = false;
	public boolean moveUp = false;

	public ConfigContainer(ConfigButton panel) {
		this.parent = panel;
		this.setHeight(0);
		this.setWidth(panel.getWidth());

	}

	private long startTime = 0;

	@Override
	public void drawElement(float posX, float posY, int mouseX, int mouseY, float partialTicks) {
		this.setPosX(posX);
		this.setPosY(posY);

		totalHeight = 0;
		for (Element e : valueList) {
			totalHeight += e.getHeight();
		}

		if (moveDown) {
			if (moveTicks == 0)
				startTime = System.currentTimeMillis();

			if (startTime < System.currentTimeMillis() + 10000)
				++moveTicks;
			else {
				moveDown = false;
				moveTicks = 0;
			}
		}

		// if (moveDown) {
		// if (moveTicks == 0) {
		// startTime = System.currentTimeMillis();
		// }
		// if (moveTicks < maxTicks && startTime)
		// ++moveTicks;
		// else {
		// moveDown = false;
		// moveTicks = 0;
		// }
		// } else if (moveUp) {
		// if (moveTicks > 0) {
		// --moveTicks;
		// } else {
		// moveUp = false;
		// this.setHeight(20);
		// isExpanded = false;
		// startTime = 0;
		// }
		// }

		if (isExpanded) {
			double total = ((getHeight() / maxTicks) * (moveDown ? moveTicks : maxTicks));
			double totalUP = ((getHeight() / maxTicks) * moveTicks);

			if (moveDown) {
				parent.setHeight((int) (22 + total));
			} else if (moveUp) {
				parent.setHeight((int) (20 + totalUP));
			}

			int height = 0;
			for (Element e : valueList) {
				e.setWidth(90);
				e.drawElement(posX + 4, posY + height + 4, mouseX, mouseY, partialTicks);
				height += e.getHeight();
			}
		}

	}

	@Override
	public float getHeight() {
		return 8 + (totalHeight);
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int button) {
		boolean overContainer = this.isExpanded && mouseX > getPosX() + 3 && mouseY > getPosY() + 2
				&& mouseX < getPosX() + getWidth() - 5 && mouseY < getPosY() + getHeight() - 2;
		if (overContainer) {
			for (Element e : valueList) {
				e.mouseReleased(mouseX, mouseY, button);
			}
		}
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		for (Element e : valueList) {
			e.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		boolean overContainer = this.isExpanded && mouseX > getPosX() + 3 && mouseY > getPosY() + 2
				&& mouseX < getPosX() + getWidth() - 5 && mouseY < getPosY() + getHeight() - 2;
		if (overContainer) {
			for (Element e : valueList) {
				e.mouseClicked(mouseX, mouseY, button);
			}
		}
	}
}
