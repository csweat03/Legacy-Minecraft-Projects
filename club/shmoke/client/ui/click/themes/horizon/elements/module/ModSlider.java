package club.shmoke.client.ui.click.themes.horizon.elements.module;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.data.Property;
import club.shmoke.client.ui.click.Element;
import club.shmoke.client.ui.click.GuiClick;
import club.shmoke.client.util.math.MathHelper;
import club.shmoke.client.util.render.GLUtils;

/**
 * Created by Apex on 8/21/2016.
 */
public class ModSlider extends Element {

	private ModContainer parent;
	private Cheat mod;
	private Property mv;
	
	boolean isOver = false;

	public ModSlider(ModContainer panel, Cheat m, Property modValue) {
		this.parent = panel;
		this.mod = m;
		this.mv = modValue;
		this.setHeight(24);
	}

	private boolean isSliding = false;

	@Override
	public void drawElement(float posX, float posY, int mouseX, int mouseY, float partialTicks) {
		posX -= 2;
		if (!(mv.getValue() instanceof Integer || mv.getValue() instanceof Double))
			return;
		int uiColor = GuiClick.color;
		this.setPosX(posX);
		this.setPosY(posY);

		double min = 0;
		double max = 0;
		if (mv.getValue() instanceof Integer) {
			min = (int) mv.getMin();
			max = (int) mv.getMax();
		} else {
			min = (double) mv.getMin();
			max = (double) mv.getMax();
		}
		float value = Float.parseFloat("" + mv.getValue());
		value = value * 2 / 2;

		double percent = (100 * ((value - min) / (max - min)));
		isOver = mouseX > posX && mouseY > posY + 8.5F && mouseX < posX + 100 && mouseY < posY + 17.5F;
		if (isOver && isSliding) {
			double oldVal = (min + (mouseX - posX - 1) * ((max - min) / 82)) * 100.0 / 100.0;
			double valueNew = (MathHelper.roundDouble(oldVal, 5));
			if (valueNew > max)
				valueNew = max;
			if (valueNew < min)
				valueNew = min;
			if (mv.getValue() instanceof Integer)
				mv.setValue((int) valueNew);
			else
				mv.setValue(valueNew);
		}
		if (!isOver && isSliding)
			isSliding = false;
		GlStateManager.pushMatrix();
		Client.INSTANCE.getFontManager().c14.drawStringWithShadow(mv.label, posX + 2, posY + 2, 0xFFCFCFCF);
		double v = 0;
		if (mv.getValue() instanceof Double)
			v = MathHelper.roundDouble((double) mv.getValue(), 3);
		String valStr = "" + v;
		if (mv.getValue() instanceof Integer)
			valStr = Integer.toString((int) mv.getValue());

		GLUtils.preState();
		Client.INSTANCE.getFontManager().c14.drawStringWithShadow(valStr,
				(posX + 82 - Client.INSTANCE.getFontManager().c16.getStringWidth(valStr)
						- (Double.parseDouble(valStr) < 10.0 ? 2 : 0)),
				posY + 2, uiColor);
		GLUtils.postState();
		GlStateManager.popMatrix();

		double valAdd = ((percent / 100) * 83);
		if (valAdd <= 2)
			valAdd = 2;

		Gui.drawBorderedRect(posX + 2, posY + 8.5F, posX + 85, posY + 13.5F, 2, 0x1AFFFFFF);
		Gui.drawBorderedGradientRect(posX + 2, posY + 8.5F, (float) (posX + 2 + valAdd), posY + 13.5F, 1, Client.INSTANCE.color.darker().darker().darker().darker().darker().darker().getRGB(), uiColor, Client.INSTANCE.color.darker().darker().darker().getRGB());
	}

	@Override
	public float getHeight() {
		return 17;
	}

	@Override
	public float getRealHeight() {
		return 18;
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (button == 0 && isOver) {
			isSliding = !isSliding;
		}

	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int button) {
		if (isSliding)
			isSliding = false;
	}
}
