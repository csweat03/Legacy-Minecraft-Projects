package com.fbiclient.fbi.impl.gui.clickable.impl.cheat.value.slider;

import com.fbiclient.fbi.impl.gui.clickable.GuiClickable;
import net.minecraft.client.gui.Gui;
import me.valkyrie.api.value.types.NumberValue;
import com.fbiclient.fbi.impl.gui.clickable.component.Component;
import com.fbiclient.fbi.impl.gui.clickable.impl.cheat.CheatButton;

import java.awt.*;

/**
 * @author Kyle
 * @since 4/15/2018
 **/
public class Slider extends Component {

    protected boolean hovered;
    protected NumberValue val;
    public CheatButton parent;
    protected boolean dragging = false;
    protected double renderWidth;

    public Slider(NumberValue value, CheatButton parent, int y) {
        this.val = value;
        this.parent = parent;
        this.y = y;
        this.height = 14;
    }

    /**
     * default rendering of a slider label and value
     */
    @Override
    public void renderComponent() {
        int left = parent.getX() + 18;
        y += GuiClickable.y - 94;
        String label = val.getFormattedLabel();
        if (val.getFormattedLabel().contains("-")) {
            label = String.valueOf(val.getFormattedLabel().split("-")[1]);
        }
        Gui.drawBorderedRect(getX(), y + 5, getX() + getWidth(), y + height - 4, 0.5, new Color(0x494949).getRGB(), new Color(0, 0, 0, 240).getRGB());
        Gui.drawRect(getX() + 1, y + 5.5, getX() + this.renderWidth - 1, y + height - 4.5, HUD.getThemeHandler().getCurrentTheme().getColor());
        String valStr = String.valueOf(this.val.getValue()).replace(".0", "");
        SMALLER.drawStringWithShadow(valStr, (float)(getX() + this.renderWidth - (FR.getWidth(valStr) / 2)), y + height - 6.5f, -1);
        SMALL.drawStringWithShadow(label, left - 11, y - 2, -1);
    }

    public void updateComponent(int mouseX, int mouseY) {
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        int left = getX();
        int right = getX() + getWidth();
        y += GuiClickable.y - 94;
        if ((mouseX > left && mouseX < right) && (mouseY < y + height && mouseY > y)) {
            switch (button) {
                case 0:
                    this.dragging = true;
                    break;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
    }

    public void keyTyped(char typedChar, int key) {
    }

    @Override
    public int getX() {
        return parent.getX() + 8;
    }

    @Override
    public int getWidth() {
        return 245;
    }

}
