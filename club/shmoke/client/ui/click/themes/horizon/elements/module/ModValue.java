package club.shmoke.client.ui.click.themes.horizon.elements.module;

import java.awt.Color;

import net.minecraft.util.ResourceLocation;
import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.client.ui.click.Element;
import club.shmoke.client.util.render.GLUtils;
import club.shmoke.client.util.render.RenderUtils;

public class ModValue extends Element {

	private ModContainer parent;
	private Cheat mod;
	private Property mv;

	boolean overToggle = false, slide = false;

	double animSlide = 0;

	int color = -1;

	public ModValue(ModContainer panel, Cheat m, Property modValue) {
		this.parent = panel;
		this.mod = m;
		this.mv = modValue;
		this.setHeight(12);
	}

	@Override
	public void drawElement(float posX, float posY, int mouseX, int mouseY, float partialTicks) {
		posX -= 5;
		posY = posY - 2;
		this.setWidth(this.getWidth() + 2);
		this.setPosX(posX);
		this.setPosY(posY);

		boolean toggled = (Boolean) mv.getValue();
		overToggle = mouseX > posX + 2.5f && mouseX < posX + 90 && mouseY > posY + 1.95f && mouseY < posY + 9.4F;

		GLUtils.preState();
		Client.INSTANCE.getFontManager().c14.drawStringWithShadow(mv.label, posX + 5, posY + 4, 0xFFCFCFCF);
		GLUtils.postState();

		posX += 70;

		if (animSlide >= 0.3 && animSlide <= 8.9)
			color = dark((int) (animSlide * 20));
		else if (animSlide < 0.3)
			color = 0x80111111;
		else
			color = Client.INSTANCE.color.getRGB();

		GLUtils.preState();
		RenderUtils.drawFullCircle(posX + 5, posY + 6.5F, 18, 4, color);
		GLUtils.postState();

		if (toggled) {
			if (animSlide <= 9)
				animSlide += 0.04F;
			else
				animSlide = 9;
		} else if (animSlide > 0) {
			animSlide -= 0.04F;
		} else if (animSlide <= 1 && !toggled) {
			animSlide = 0;
		}
		GLUtils.preState();
		RenderUtils.drawCustomImage(posX + 1.3 + animSlide, posY + 2.6F, 7.3, 7.3,
				new ResourceLocation("client/click/bubble.png"));
		GLUtils.postState();

		// if(toggled)
		// RenderUtils.drawFullCircle(posX + 12F, posY + 1.5F, 4, 4,
		// Client.INSTANCE.color.getRGB());
		// //RenderUtils.drawFullCircle(posX + 12, posY + 1.5, posX + 22, posY + 11.5,
		// Client.INSTANCE.color.getRGB());
		// else
		// Gui.drawRoundedRect(posX + 2.5, posY + 1.5, posX + 12, posY + 11.5,
		// Client.INSTANCE.color.darker().getRGB(), Client.INSTANCE.color.getRGB());

		// RenderUtils.drawBorderedRect(posX + 11, posY + 1, posX + 21, posY + 11, 1,
		// 0x90000000, 0xbb000000);

		// if (toggled)
		// RenderUtils.drawCheckmark(posX + 11.4F, posY + 5f, 0xffbbbbbb);
	}

	@Override
	public float getHeight() {
		return 13;
	}

	@Override
	public float getRealHeight() {
		return 12;
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (button == 0 && overToggle)
			mv.setValue(!(boolean) mv.getValue());
	}

	private int dark(int count) {
		Color color = Client.INSTANCE.color;
		for (int i = 0; i <= count; i++) {
			color = new Color(color.getRed(), color.getGreen(), color.getBlue(), i);
		}
		return color.getRGB();
	}

}
